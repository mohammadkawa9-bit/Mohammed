package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.api.GeminiApiClient
import com.example.api.GenerateContentRequest
import com.example.api.Content
import com.example.api.Part
import com.example.data.AppDatabase
import com.example.data.Bookmark
import com.example.data.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ChatMessage(
    val sender: String, // "user" or "ai"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

class ReberViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = BookmarkRepository(db.bookmarkDao())

    val bookmarks: StateFlow<List<Bookmark>> = repository.allBookmarks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                sender = "ai",
                text = "سڵاو لە ئێوەی بەڕێز! من یاریدەدەری شەرعی و مێژوویی (ڕێبەر)م. لە هەر بابەتێکی فیقهی، مێژووی ئیسلامی، خواردەمەنییە قەدەغەکراوەکانی قورئان، جیاوازی ئیمان و ئیسلام، یان تەنانەت نزا و نزاکان پرسیارتان هەیە فەرموون دیاری بکەن."
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _ingredientResult = MutableStateFlow<String?>("چاوەڕێی هاتنە ناوەوەی پێکهاتەیە...")
    val ingredientResult: StateFlow<String?> = _ingredientResult.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    // Ramadan Generator State
    private val _ramadanFormattedText = MutableStateFlow<String>("")
    val ramadanFormattedText: StateFlow<String> = _ramadanFormattedText.asStateFlow()

    init {
        // Generate pre-populated Ramadan statement matching user's custom hours
        generateRamadanNotice("٠٨:٠٠", "٠٢:٠٠", "٠٩:٠٠", "٠٣:٠٠")
    }

    fun sendChatMessage(userText: String) {
        if (userText.isBlank()) return

        val userMsg = ChatMessage("user", userText)
        _chatMessages.update { it + userMsg }
        _isGenerating.value = true

        viewModelScope.launch {
            try {
                val apiKey = BuildConfig.GEMINI_API_KEY
                if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
                    _chatMessages.update {
                        it + ChatMessage(
                            "ai",
                            "سەرنج: کلیل کەرتی نێو نهێنییەکان (GEMINI_API_KEY) چالاک نەکراوە. تکایە کلیلی ڕاستەقینە لە پانێڵی نهێنییەکان بنووسە."
                        )
                    }
                    _isGenerating.value = false
                    return@launch
                }

                // Compile history context for Gemini REST API call
                val contentsList = mutableListOf<Content>()
                _chatMessages.value.takeLast(10).forEach { msg ->
                    val roleString = if (msg.sender == "user") "user" else "model"
                    contentsList.add(
                        Content(parts = listOf(Part(text = msg.text)))
                    )
                }

                val systemInstruction = Content(
                    parts = listOf(
                        Part(
                            text = "You are 'Rêber', a dignified Kurdish Islamic scholar. Answer questions thoroughly and exclusively in high-quality, professional Kurdish (Sorani script). Use appropriate Islamic terminology. Keep explanations polite, grounded in scholarly consensus (such as the schools of jurisprudence, Ibn Taymiyyah, Imam Al-Nawawi, and Tafsir). Maintain a warm and guidance-oriented persona."
                        )
                    )
                )

                val request = GenerateContentRequest(
                    contents = contentsList,
                    systemInstruction = systemInstruction
                )

                val response = withContext(Dispatchers.IO) {
                    GeminiApiClient.service.generateContent(apiKey, request)
                }

                val answer = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    ?: "بۆردەکە وەڵامێکی گونجاوی نەبوو."

                _chatMessages.update { it + ChatMessage("ai", answer) }
            } catch (e: Exception) {
                _chatMessages.update {
                    it + ChatMessage(
                        "ai",
                        "کێشەیەک ڕوویدا لە پێوەندیکردن بە زیرەکی دەستکرد: ${e.localizedMessage ?: "هەڵەکە نادیارە"}"
                    )
                }
            } finally {
                _isGenerating.value = false
            }
        }
    }

    fun checkIngredients(ingredients: String) {
        if (ingredients.isBlank()) return
        _isScanning.value = true
        _ingredientResult.value = "لە پشکنیندایە..."

        viewModelScope.launch {
            try {
                val apiKey = BuildConfig.GEMINI_API_KEY
                if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
                    _ingredientResult.value = "کلیل پشێوی تێپەڕاندووە. تکایە کلیلی زیرەکی دەستکرد لە پانێڵ ڕێکبخە."
                    _isScanning.value = false
                    return@launch
                }

                val prompt = """
                    پشکنینی شەرعی و تەندروستی ئەم پێکهاتانە یان ناوبرندە بکە بۆ جەستەی مرۆڤ و خاوێنی:
                    $ingredients
                """.trimIndent()

                val request = GenerateContentRequest(
                    contents = listOf(Content(parts = listOf(Part(text = prompt)))),
                    systemInstruction = Content(
                        parts = listOf(
                            Part(
                                text = "You are an expert Islamic dietary auditor. Analyze inputs (like brands, gelatin, food colors, preservation chemicals, meat status, or products like Maggi soup/cubes) to determine if they are Halal (حەڵاڵ), Haram (حەرام), or Doubtful (گوماناوی/شوبھە) from a Shariah & hygiene perspective. Reference verses (Al-Ma'idah:3, Al-Hujurat:12), heavy metals safety, high Sodium risks, and compare premium audited brands to unchecked counterfeits. Keep the analysis strictly in Kurdish, concise, and divided with clean bullet points. Clearly announce the overall ruling first."
                            )
                        )
                    )
                )

                val response = withContext(Dispatchers.IO) {
                    GeminiApiClient.service.generateContent(apiKey, request)
                }

                val answer = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    ?: "هیچ وەڵامێک وەرنەگیرا."
                _ingredientResult.value = answer
            } catch (e: Exception) {
                _ingredientResult.value = "هەڵە لە پشکنین: ${e.localizedMessage}"
            } finally {
                _isScanning.value = false
            }
        }
    }

    fun generateRamadanNotice(
        branchStart: String,
        branchEnd: String,
        salesStart: String,
        salesEnd: String
    ) {
        val notice = """
            هاوکارانی بەڕێز،
            
            بەبۆنەی نزیکبوونەوەی مانگی پیرۆزی ڕەمەزان، گەرمترین پیرۆزباییتان ئاراستە دەکەین. هیوای ڕۆژوویەکی قبووڵکراو و ڕۆژانێکی پڕ لە خێر و بەرەکەتتان بۆ دەخوازین.
            
            لەپێناو ئاسودەیی ئێوە و ئاسانکاری بۆ ڕاپەڕاندنی کارەکانتان لەم مانگە پیرۆزەدا، ئاگادارتان دەکەینەوە کە کاتەکانی دەوامی فەرمی بەم شێوەیەی خوارەوە دەبێت:
            
            ⏰ کاتەکانی دەوام لە مانگی ڕەمەزاندا:
            - گشت لقەکان: لە کاتژمێر ${branchStart}ی بەیانی تاوەکو ${branchEnd}ی پاشنیوەڕۆ.
            - تیمی فرۆشتن لە لقەکانی (هەولێر، دهۆک، و موسڵ): دەوامیان لە کاتژمێر ${salesStart}ی بەیانی تاوەکو ${salesEnd}ی دوای نیوەڕۆ دەبێت.
            
            سوپاسی هاوکاریتان دەکەین و هیوای مانگێکی پیرۆز و پڕ لە سەرکەوتنتان بۆ دەخوازین.
            
            لەگەڵ ڕێزماندا،
        """.trimIndent()
        _ramadanFormattedText.value = notice
    }

    // Bookmarking logic
    fun toggleBookmark(type: String, title: String, subtitle: String, details: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val alreadyBookmarked = repository.isBookmarked(type, title).first()
            if (alreadyBookmarked) {
                repository.deleteByTypeAndTitle(type, title)
            } else {
                repository.insert(
                    Bookmark(
                        type = type,
                        title = title,
                        subtitle = subtitle,
                        details = details
                    )
                )
            }
        }
    }

    fun isBookmarked(type: String, title: String): Flow<Boolean> {
        return repository.isBookmarked(type, title)
    }

    fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(bookmark)
        }
    }

    fun clearChat() {
        _chatMessages.value = listOf(
            ChatMessage(
                sender = "ai",
                text = "سڵاو لە ئێوەی بەڕێز! من یاریدەدەری شەرعیی و مێژوویی (ڕێبەر)م. لە هەر بابەتێکی فیقهی، مێژووی ئیسلامی، خواردەمەنییە قەدەغەکراوەکانی قورئان، جیاوازی ئیمان و ئیسلام پرسیارتان هەیە فەرموون دیاری بکەن."
            )
        )
    }
}
