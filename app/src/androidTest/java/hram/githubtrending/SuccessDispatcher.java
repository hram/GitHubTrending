package hram.githubtrending;


import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

/**
 * @author Evgeny Khramov
 */
public class SuccessDispatcher extends Dispatcher {

    private final BaseMockTest mBaseMockTest;

    public SuccessDispatcher(@NonNull BaseMockTest baseMockTest) {
        this.mBaseMockTest = baseMockTest;
    }

    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        try {
            switch (request.getPath()) {
                case "/trending/":
                    return mBaseMockTest.getResponse("trending_200.html");
                case "/trending/java?since=daily":
                    return mBaseMockTest.getResponse("java_daily_200.html");
                case "/trending/java?since=weekly":
                    break;
                case "/trending/java?since=monthly":
                    break;
            }
        } catch (IOException e) {
            // do nothing
        }

        return new MockResponse().setResponseCode(404);
    }
}
