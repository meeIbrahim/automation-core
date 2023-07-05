package postgresql.parameters;

import com.google.gson.annotations.SerializedName;

public class PostgreSQLConnectionParameters {

    @SerializedName("Remote Host")
    public RemoteHostParameters remoteHostParameters = new RemoteHostParameters();

    @SerializedName("Port Forwarding")
    public PortForwardingParameters portForwardingParameters = new PortForwardingParameters();

    @SerializedName("Database")
    public DatabaseParameters databaseParameters = new DatabaseParameters();
}
