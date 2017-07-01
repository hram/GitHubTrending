package hram.githubtrending;

import android.support.annotation.NonNull;

import org.junit.Rule;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * @author Evgeny Khramov
 */

public class BaseMockTest extends BaseTest {

    @Rule
    public final MockWebServer mServer = new MockWebServer();

    @NonNull
    MockResponse getResponse(@NonNull String fileName) throws IOException {
        return new MockResponse().setBody(readString(fileName));
    }

    public String readString(String fileName) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        try {
            int size = stream.available();
            byte[] buffer = new byte[size];
            int result = stream.read(buffer);
            return new String(buffer, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
