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

    private final BaseMockTest baseMockTest;

    public SuccessDispatcher(@NonNull BaseMockTest baseMockTest) {
        this.baseMockTest = baseMockTest;
    }

    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        try {
            switch (request.getPath()) {
                case "/trending/":
                    return baseMockTest.getResponse("trending_200.html");
                case "/trending/java?since=daily":
                    return baseMockTest.getResponse("java_daily_200.html");
                case "/trending/java?since=weekly":
                    break;
                case "/trending/java?since=monthly":
                    break;
            }
        } catch (IOException e) {
        }

        return new MockResponse().setResponseCode(404);
    }
}
