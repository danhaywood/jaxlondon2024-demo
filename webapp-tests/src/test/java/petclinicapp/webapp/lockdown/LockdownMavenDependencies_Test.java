package petclinicapp.webapp.lockdown;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.val;


/**
 * prereqs:
 * <pre>
 *  sh generate-lockdown.sh
 * </pre>
 */
public class LockdownMavenDependencies_Test {

    @BeforeEach
    public void setup() {
         Assumptions.assumeThat(System.getProperty("lockdown")).isNotNull();
    }

    @UseReporter(DiffReporter.class)
    @Test
    public void list() throws Exception {
        Approvals.verify(sort(read("list")));
    }

    @UseReporter(DiffReporter.class)
    @Test
    public void tree() throws Exception {
        Approvals.verify(read("tree"));
    }

    private String read(final String goal) throws IOException {
        final URL resource = Resources.getResource(
                getClass(),
                String.format("%s.%s.actual.txt", getClass().getSimpleName(), goal));
        return Resources.toString(resource, Charsets.UTF_8);
    }

    private static String sort(final String unsorted) {
        val lineArr = unsorted.split("[\r\n]+");
        val lineList = new ArrayList<>(Arrays.asList(lineArr));
        return lineList.stream()
                .map(x -> sanitize(x))
                .sorted()
                .collect(Collectors.joining("\n"));
    }

    static String regex = "(?<prefix>.*?)(?<suffix> -- module.*)?$";
    static Pattern pattern = Pattern.compile(regex);

    private static String sanitize(String input) {
        val matcher = pattern.matcher(input);
        return matcher.matches()
                ? matcher.group("prefix")
                : input;
    }

}
