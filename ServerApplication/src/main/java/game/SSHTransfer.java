package game;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SSHTransfer {


public void transferFile(String fileName) {
    try {
        JSch ssh = new JSch();
        Session session = ssh.getSession("username", "localhost", 22);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.setPassword("Passw0rd");
        session.connect();
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;
        File f = new File(fileName);
        sftp.put(new FileInputStream(f), f.getName());

        Boolean success = true;

        if (success) {
        }

        channel.disconnect();
        session.disconnect();
    } catch (JSchException e) {
        System.out.println(e.getMessage().toString());
        e.printStackTrace();
    } catch (SftpException e) {
        System.out.println(e.getMessage().toString());
        e.printStackTrace();
    }
    catch (IOException e){
        System.out.println(e.getMessage().toString());
        e.printStackTrace();
    }
}
}
