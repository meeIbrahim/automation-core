package jsch;

public class PortForwardBuilder {
    protected Integer localPort;
    protected Integer remotePort;
    protected String host;

    public PortForwardBuilder localPort(Integer localPort) {
        this.localPort = localPort;
        return this;
    }

    public PortForwardBuilder remotePort(Integer remotePort) {
        this.remotePort = remotePort;
        return this;
    }

    public PortForwardBuilder host(String host) {
        this.host = host;
        return this;
    }

    public ForwardPort build() {
        return new ForwardPort(this);
    }
}
