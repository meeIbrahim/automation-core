package maverick;

public class portForward {
    public Integer Port;
    public remoteHost Host;


    public portForward(remoteHost Host, Integer LocalPort,String RemoteAddress, Integer RemotePort){
        Host.LocalPortForward(RemoteAddress,LocalPort,RemotePort);
        this.Port = LocalPort;
        this.Host = Host;
    }

    public portForward(remoteHost Host, String RemoteAddress, Integer RemotePort){
        this.Port = Host.LocalPortForward(RemoteAddress,RemotePort);
        this.Host = Host;
    }

    public void Stop(){
        this.Host.StopLocalPortForwarding();
    }

////////////////////////////////////////    BUILDER CLASS         //////////////////////////////////////////////////////
    public static Parameters With(){
            return new Parameters();
    }
    public static class Parameters {
        private String RemoteAddress;
        private Integer LocalPort;
        private Integer RemotePort;

        public Parameters(){
            this.LocalPort = 0;
            this.RemoteAddress = "127.0.0.1";
        }
        public Parameters RemoteAddress(String Address){
            this.RemoteAddress = Address;
            return this;
        }
        public Parameters RemotePort(Integer Port){
            this.RemotePort = Port;
            return this;
        }
        public Parameters LocalPort(Integer Port){
            this.LocalPort = Port;
            return this;
        }

        public portForward UsingHost(remoteHost Host){
            if (this.LocalPort == 0 ){return new portForward(Host,this.RemoteAddress,this.RemotePort);}
            else {return new portForward(Host,this.LocalPort,this.RemoteAddress,this.RemotePort);}
        }

        public portForward UsingHost(remoteHost.ConnectionParameters Host){
            if (this.LocalPort == 0 ){return new portForward(Host.Connect(),this.RemoteAddress,this.RemotePort);}
            else {return new portForward(Host.Connect(),this.LocalPort,this.RemoteAddress,this.RemotePort);}
        }
    }

}
