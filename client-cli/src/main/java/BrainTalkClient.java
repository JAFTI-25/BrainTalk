import ru.jafti.braintalk.cli.command.Commands;
import ru.jafti.braintalk.cli.out.TerminalOutput;
import ru.jafti.braintalk.cli.out.UserOutput;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Scanner;

public class BrainTalkClient {

    private Commands commands = Commands.INSTANCE;
    private UserOutput out = UserOutput.Impl.get();

    public void start() {
        out.print("Cli app started");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            commands.execute(scanner.nextLine());
        }
    }

    public static void main(String[] args) {
        new BrainTalkClient().start();
    }
}