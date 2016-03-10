package checksum.remotecommandexecution;

/**
 * Created by alcohol on 3/7/16.
 */

import static org.junit.Assert.*;
import org.junit.Test;
import org.omg.CORBA.portable.Streamable;

import java.util.List;

public class CommandExecutorTest {
    private static String userName = "student";
    private static String password = "Cmpe86754231!";
    private static String host = "130.65.159.58";
    private static int port = 22;
    private CommandExecutor commandExecutor = new CommandExecutor(userName, password, host);

    @Test
    public void executeCommandTest() {
        String command = "pwd";
        String expected = "/home/student";
        List<String> output = this.commandExecutor.executeCommand(command);
        System.out.println(output.toString());
        assertEquals(expected, commandExecutor.executeCommand(command).get(0));
    }

    @Test
    public void executeCommandWithSudoAndArgumentsTest() {
        String command = "sha256sum";
        String path = "/var/lib/glance/images/7f6201b7-94ba-4dc9-96fa-bb2003fe152f";
        String expected = "c16a1325bf6a42fe53d8826f289759c45bc11f25782d741704354cf1f935850b  /var/lib/glance/images/7f6201b7-94ba-4dc9-96fa-bb2003fe152f";
        List<String> output = this.commandExecutor.executeCommandWithSudo(command, "Cmpe86754231!", path);
        System.out.println(output.toString());
        assertEquals(expected, output.get(0));
    }

}