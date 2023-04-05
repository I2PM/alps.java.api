package alps.java.api.util;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An ASCII progress bar
 */
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An ASCII Progress bar
 */
public class ConsoleProgressBar implements AutoCloseable {
    private static final int BLOCK_COUNT = 10;
    private static final long ANIMATION_INTERVAL = 1000 / 8; // Milliseconds
    private static final String ANIMATION = "|/-\\";

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final AtomicReference<Double> currentProgress = new AtomicReference<>(0.0);
    private String currentText = "";
    private int animationIndex = 0;

    public ConsoleProgressBar() {
        scheduler.scheduleAtFixedRate(this::timerHandler, ANIMATION_INTERVAL, ANIMATION_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public void report(double value) {
        // Make sure value is in [0..1] range
        value = Math.max(0, Math.min(1, value));
        currentProgress.set(value);
    }

    private void timerHandler() {
        int progressBlockCount = (int) (currentProgress.get() * BLOCK_COUNT);
        int percent = (int) (currentProgress.get() * 100);
        String text = "[" +
                "█".repeat(Math.max(0, progressBlockCount)) +
                "-".repeat(Math.max(0, BLOCK_COUNT - progressBlockCount)) +
                "]" +
                String.format("%3d", percent) + "% " + ANIMATION.charAt(animationIndex++ % ANIMATION.length());
        updateText(text);
    }

    private void updateText(String text) {
        // Get length of common portion
        int commonPrefixLength = 0;
        int commonLength = Math.min(currentText.length(), text.length());
        while (commonPrefixLength < commonLength && text.charAt(commonPrefixLength) == currentText.charAt(commonPrefixLength)) {
            commonPrefixLength++;
        }

        // Backtrack to the first differing character
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("\b".repeat(Math.max(0, currentText.length() - commonPrefixLength)));

        // Output new suffix
        outputBuilder.append(text.substring(commonPrefixLength));

        // If the new text is shorter than the old one: delete overlapping characters
        int overlapCount = currentText.length() - text.length();
        if (overlapCount > 0) {
            outputBuilder.append(" ".repeat(overlapCount));
            outputBuilder.append("\b".repeat(overlapCount));
        }

        System.out.print(outputBuilder);
        currentText = text;
    }

    @Override
    public void close() {
        scheduler.shutdown();
        updateText("");
    }
}

/*
public class ConsoleProgressBar implements AutoCloseable {
    private final int BLOCK_COUNT = 10;
    private final Duration ANIMATION_INTERVAL = Duration.ofSeconds(long(1.0 / 8));
    private final String ANIMATION = "|/-\";

    private final Timer timer;

    private double currentProgress = 0;
    private String currentText = "";
    private boolean disposed = false;
    private int animationIndex = 0;

    public ConsoleProgressBar() {
        timer = new Timer();


         /*
         * A progress bar is only for temporary display in a console window.
         * If the console output is redirected to a file, draw nothing.
         * Otherwise, we'll end up with a lot of garbage in the target file.


        Console console = System.console();
        if (console != null && !console.writer().checkError()) {
            resetTimer();
        }
    }

    public void report(double value) {
        // Make sure value is in [0..1] range
        value = Math.max(0, Math.min(1, value));
        currentProgress = value;
    }

    private void timerHandler() {
        synchronized (timer) {
            if (disposed) return;

            int progressBlockCount = (int) (currentProgress * BLOCK_COUNT);
            int percent = (int) (currentProgress * 100);
            String text =
                    "[" + new String(new char[progressBlockCount]).replace("\0", "#") +
                            new String(new char[BLOCK_COUNT - progressBlockCount]).replace("\0", "-") + "]" +
                            String.format("%3d%% %s", percent, ANIMATION.charAt(animationIndex++ % ANIMATION.length()));
            updateText(text);

            resetTimer();
        }
    }
    private void updateText(String text) {
        // Get length of common portion
        int commonPrefixLength = 0;
        int commonLength = Math.min(currentText.length(), text.length());
        while (commonPrefixLength < commonLength && text.charAt(commonPrefixLength) == currentText.charAt(commonPrefixLength)) {
            commonPrefixLength++;
        }

        // Backtrack to the first differing character
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(String.format("\b%0" + (currentText.length() - commonPrefixLength) + "d", 0).replace("0", "\\b"));

        // Output new suffix
        outputBuilder.append(text.substring(commonPrefixLength));

        // If the new text is shorter than the old one: delete overlapping characters
        int overlapCount = currentText.length() - text.length();
        if (overlapCount > 0) {
            outputBuilder.append(String.format("%0" + overlapCount + "d", 0).replace("0", " "));
            outputBuilder.append(String.format("\b%0" + overlapCount + "d", 0).replace("0", "\\b"));
        }
    }
    private void resetTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timerHandler();
            }
        }, animationInterval.toMillis(), animationInterval.toMillis());
    }

    @Override
    public void close() {
        synchronized (timer) {
            disposed = true;
            updateText("");
        }
    }

}*/

