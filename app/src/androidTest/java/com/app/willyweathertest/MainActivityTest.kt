package com.app.willyweathertest

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.app.willyweathertest.network.ApiInterface
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val server: MockWebServer = MockWebServer()
    lateinit var apiInterface: ApiInterface

    val mainActivityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Before
    fun init(){
        server.start()

        apiInterface = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ApiInterface::class.java)


    }
    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun parseJsonResponse() {
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("JobsResponse.json").content))
        }
        apiInterface.fetchJobs()
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun checkTitle() {
        assertDisplayed("Willy Weather")
    }
    @Test
    fun openDetailPage(){
        clickListItem(R.id.rv_jobs,1)
    }
    @Test
    fun scrollList(){
        scrollListToPosition(R.id.rv_jobs,4);
    }
}