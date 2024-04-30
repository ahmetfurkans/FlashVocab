package com.svmsoftware.flashvocab.core.domain.use_cases

import com.google.cloud.http.BaseHttpServiceException
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateException
import com.google.cloud.translate.TranslateOptions
import com.google.cloud.translate.Translation
import com.svmsoftware.flashvocab.core.util.Resource

/*
    This function must call always background thread.
 */
class ProcessTranslate {

    operator fun  invoke(
        targetLang: String, source: String
    ): Resource<String> {
        return try {
            if (source.isBlank()) {
                return Resource.Error("You should type something.")
            }
            val API_KEY = "AIzaSyCBrBgTX8KYYz9MsahpDc7vOy_8eeb3QqU"

            val translate = TranslateOptions.newBuilder().setApiKey(API_KEY).build().service
            val translation: Translation = translate.translate(
                source,
                Translate.TranslateOption.targetLanguage(targetLang)
            )
            Resource.Success(translation.translatedText)
        } catch (e: TranslateException) {
            Resource.Error(desc = e.message.toString())
        } catch (e: BaseHttpServiceException) {
            Resource.Error(desc = e.message.toString())
        } catch (e: Exception) {
            Resource.Error(desc = e.message.toString())
        }
    }
}

