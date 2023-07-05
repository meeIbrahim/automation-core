package jsch;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ForwardPort {
    private Integer localPort;
    private Integer remotePort;
    private String host;

    public ForwardPort(PortForwardBuilder builder) {
        this.localPort = builder.localPort;
        this.remotePort = builder.remotePort;
        this.host = builder.host;
    }

    public Integer forSession(Session session) throws JSchException {
        Integer forwarded_port = session.setPortForwardingL(localPort, host, remotePort);
        System.out.println("Port Forwarded: "+"localhost:"+forwarded_port+" -> "+host+":"+remotePort);
        return forwarded_port;
    }
}
