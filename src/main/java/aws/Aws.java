package aws;

import lombok.SneakyThrows;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static aws.Ec2.describeEC2Instance;

/*
    AWS Credentials are required to be set in AWS Credentials file
    If no Profile is specified, function will look for '<env>-automation' profile
 */
public class Aws {
    public static List<String> getRelay(String Name){
        EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
        String AWSProfile = env + "-automation";
        return getRelay(Name,AWSProfile);
    }
    public static List<String> getRelay(String Name,String profile){
        Ec2Client ec = Ec2Client.builder().credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
        String RelayName = "relay-node-" + Name;
        String Tag = "tag:aws:cloudformation:stack-name";
        return describeEC2Instance(ec,Tag,RelayName);
    }
    public static void getAllRunningInstances(String profile){
        Ec2Client ec = Ec2Client.builder().credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
        Ec2.describeEC2Instances(ec);
    }
    public static void stopEC2Instance(String instance_id,String profile){
        Ec2Client ec = Ec2Client.builder().credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
        if (Ec2.instanceState(ec,instance_id).equals("stopped")){
           System.out.println("Instance "+instance_id+" is already stopped");
        }else{
            Ec2.stopInstance(ec,instance_id);
        }
    }
    public static void startEC2Instance(String instance_id,String profile) {
        Ec2Client ec = Ec2Client.builder().credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
        if (Ec2.instanceState(ec, instance_id).equalsIgnoreCase("running")) {
            System.out.println("Instance " + instance_id + " is already Running");
        } else {
            Ec2.startInstance(ec, instance_id);
        }
    }

    public static void terminateEC2Instance(String instance_id,String profile){
        Ec2Client ec = Ec2Client.builder().credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
        Ec2.terminateInstance(ec,instance_id);
    }
    public static void disassociateElasticIp(String instance_id,String profile) {
        Ec2Client ec = Ec2Client.builder().credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
        Ec2.disassociateAddress(ec, instance_id);
    }
    public static void changeType(String instance_id,String profile) {
        Ec2Client ec = Ec2Client.builder().credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
        if (Ec2.instanceState(ec,instance_id).equals("running")){
            System.out.println("Instance "+instance_id+" is already running. Stopping it before changing type");
            Ec2.stopInstance(ec,instance_id);
        }
        Ec2.changeInstanceType(ec, instance_id);
        Ec2.startInstance(ec,instance_id);
    }

    public static void associateElasticIp(String instance_id,String profile,String allocation_id) {
        Ec2Client ec = Ec2Client.builder().credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
        Ec2.associateAddress(ec, instance_id,allocation_id);
    }


    @SneakyThrows
    public static void rebootEC2Instance(String instance_id, String profile) {
        Ec2Client ec = Ec2Client.builder().credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
        if (Ec2.instanceState(ec,instance_id).equals("stopped")){
            Ec2.startInstance(ec,instance_id);
            TimeUnit.SECONDS.sleep(60);
            Ec2.rebootInstance(ec,instance_id);
        }else{
            Ec2.rebootInstance(ec,instance_id);
        }

    }

    @SneakyThrows
    public static String getInstanceId(String instance_name, String profile) {
        Ec2Client ec = Ec2Client.builder().credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
            return Ec2.instanceId(ec,instance_name);

    }

}
