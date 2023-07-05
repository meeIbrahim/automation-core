package OS;

import java.io.IOException;
import java.net.ServerSocket;

public class Ports {

        /// Find any free Port on Machine
        public static int FreePort(){
            try (ServerSocket socket = new ServerSocket(0)) {           /// For Concurrent Runs, might need to avoid auto resource management inside try block
                socket.setReuseAddress(true);                               /// Allows the Port to be reused by required service after closing the socket
                return socket.getLocalPort();
            } catch (IOException ignored) {
            }
            throw new IllegalStateException("Could not find a free TCP/IP");
        }
}
