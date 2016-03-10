package checksum.remotecommandexecution;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by alcohol on 3/7/16.
 */
public class CommandExecutor {
    private String userName, password, host;
    private int port = 22;

    public CommandExecutor(String userName, String password, String host) {
        this.userName = userName;
        this.password = password;
        this.host = host;
    }
    //Constructor to override default ssh port (port 22)
    public CommandExecutor(String userName, String password, String host, int port) {
        this.userName = userName;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public List<String> executeCommand(String commandName) {
        return this.executeCommand(commandName, null);
    }

    public List<String> executeCommand(String commandName, String... args) {
        List<String> output = new ArrayList<String>();
        String argument = null;
        if(args != null) argument = this.buildArgument(Arrays.asList(args));
        try {
            JSch jsch = new JSch();

            /*
            * Open a new session, with your username, host and port
            * Set the password and call connect.
            * session.connect() opens a new connection to remote SSH server.
            * Once the connection is established, you can initiate a new channel.
            * this channel is needed to connect to remotely execution program
            */
            Session session = jsch.getSession(userName, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            //create the execution channel over the session
            ChannelExec channelExec = (ChannelExec)session.openChannel("exec");

            // Gets an InputStream for this channel. All data arriving in as messages from the remote side can be read from this stream.
            InputStream in = channelExec.getInputStream();
            OutputStream out= channelExec.getOutputStream();
            PrintWriter pw = new PrintWriter(out);
            // Set the command that you want to execute
            if(argument != null) channelExec.setCommand(commandName + " " + argument);
            else channelExec.setCommand(commandName);

            // Execute the command
            channelExec.connect();

            pw.write(password + "\n");
            pw.flush();

            // Read the output from the input stream we set above
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            //Read each line from the buffered reader and add it to result list
            // You can also simple print the result here
            while ((line = reader.readLine()) != null)
            {
                output.add(line);
            }

            //retrieve the exit status of the remote command corresponding to this channel
            int exitStatus = channelExec.getExitStatus();

            //Safely disconnect channel and disconnect session. If not done then it may cause resource leak
            channelExec.disconnect();
            session.disconnect();

            if(exitStatus < 0){
                System.out.println("Done, but exit status not set!");
            }
            else if(exitStatus > 0){
                System.out.println("Done, but with error!");
            }
            else{
                System.out.println("Done!");
            }

        } catch (Exception E) {
            System.err.println("Error: " + E);
        }
        return output;
    }

    public List<String> executeCommandWithSudo(String commandName, String sudoPassword) {
        return this.executeCommandWithSudo(commandName, sudoPassword, null);
    }

    public List<String> executeCommandWithSudo(String commandName, String sudoPassword, String... args) {
        List<String> output = new ArrayList<String>();
        String argument = null;
        if(args != null) argument = this.buildArgument(Arrays.asList(args));
        try {
            JSch jsch = new JSch();

            Session session = jsch.getSession(userName, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            ChannelExec channelExec = (ChannelExec)session.openChannel("exec");

            InputStream in = channelExec.getInputStream();
            OutputStream out= channelExec.getOutputStream();
            PrintWriter pw = new PrintWriter(out);

            if(argument != null) channelExec.setCommand("sudo -S -p '' " + commandName + " " + argument);
            else channelExec.setCommand("sudo -S -p '' " + commandName);

            channelExec.connect();

            pw.write(sudoPassword + "\n");
            pw.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = reader.readLine()) != null)
            {
                output.add(line);
            }

            int exitStatus = channelExec.getExitStatus();

            channelExec.disconnect();
            session.disconnect();

            if(exitStatus < 0){
                //System.out.println("Done, but exit status not set!");
            }
            else if(exitStatus > 0){
                //System.out.println("Done, but with error!");
            }
            else{
                System.out.println("Done!");
            }

        } catch (Exception E) {
            System.err.println("Error: " + E);
        }
        return output;
    }

    private String buildArgument(List<String> args) {
        StringBuilder output = new StringBuilder();
        Iterator<String> it = args.listIterator();
        while (it.hasNext()) {
            output.append(it.next());
            if(it.hasNext()) output.append(" ");
        }
        return output.toString();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}