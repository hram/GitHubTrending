package hram.githubtrending;

import android.support.annotation.NonNull;
import android.util.LongSparseArray;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.List;

import hram.githubtrending.data.DataManager;
import hram.githubtrending.viewmodel.RepositoryViewModel;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Evgeny Khramov
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 21, application = TestApplication.class)
public class TelegramBotClient extends BaseTest {

    private static final String DB_FILE_NAME = "database.db";

    private File mCopyDataBase;

    private File mCurrentDataBase;

    private TelegramBot mBot;

    @Before
    public void setup() throws IOException {
        mBot = TelegramBotAdapter.build(BuildConfig.TELEGRAM_BOT_TOKEN);
        mCopyDataBase = new File(DB_FILE_NAME);
        mCurrentDataBase = RuntimeEnvironment.application.getDatabasePath(DB_FILE_NAME);
        if (mCopyDataBase.exists()) {
            copyFile(mCopyDataBase, mCurrentDataBase);
        }
    }

    @After
    public void breakdown() throws IOException {
        copyFile(mCurrentDataBase, mCopyDataBase);
    }


    @Test
    public void sendMessagesForAll() throws URISyntaxException, IOException {
        assertThat(DataManager.getInstance().getParams().isEmpty(), is(false));
        assertThat(DataManager.getInstance().getParams().getLanguage(), is("java"));
        assertThat(DataManager.getInstance().getParams().getTimeSpan(), is("daily"));

        final List<Update> updates = getUpdates();

        final TestObserver<List<RepositoryViewModel>> repositories = DataManager.getInstance().refreshRepositories().test();
        repositories.awaitTerminalEvent();
        repositories.assertNoErrors();

        for (RepositoryViewModel viewModel : repositories.values().get(0)) {
            TestObserver<Boolean> res = DataManager.getInstance().setHided(viewModel.getId(), true).test();
            res.awaitTerminalEvent();
            res.assertNoErrors().assertResult(true);

            final LongSparseArray<Update> array = new LongSparseArray<>();
            for (Update update : updates) {
                if (array.get(update.message().chat().id()) != null) {
                    continue;
                }

                sendMessage(update.message().chat(), formatMessage(viewModel));
                array.put(update.message().chat().id(), update);
            }
        }
    }

    @NonNull
    private List<Update> getUpdates() {
        GetUpdates getUpdates = new GetUpdates().limit(100).offset(0).timeout(0);
        GetUpdatesResponse updatesResponse = mBot.execute(getUpdates);
        List<Update> updates = updatesResponse.updates();
        assertThat(updates.isEmpty(), is(false));
        return updates;
    }

    @NonNull
    private String formatMessage(@NonNull RepositoryViewModel viewModel) {
        return String.format("<a href=\"%s\">%s</a>\n%s\n\nStars: %s\nForks: %s\n%s", BuildConfig.URL_BASE + viewModel.getId(), viewModel.getTitle(), viewModel.getDescription(), viewModel.getAllStars(), viewModel.getForks(), viewModel.getStarsToday());
    }

    private void sendMessage(@NonNull Chat chat, @NonNull String message) {
        SendMessage request = new SendMessage(chat.id(), message)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true);

        SendResponse sendResponse = mBot.execute(request);
        assertThat(sendResponse.isOk(), is(true));
    }

    private static void copyFile(@NonNull File sourceFile, @NonNull File destFile) throws IOException {
        if (!destFile.exists()) {
            if (destFile.getParentFile() != null && !destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }
}
