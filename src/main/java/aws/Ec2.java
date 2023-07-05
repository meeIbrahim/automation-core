package aws;

import lombok.SneakyThrows;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Ec2 {

    public static void describeEC2Instances(Ec2Client ec2) {
        String nextToken = null;
        try {
            do {
                DescribeInstancesRequest request = DescribeInstancesRequest.builder().maxResults(6).nextToken(nextToken).build();
                DescribeInstancesResponse response = ec2.describeInstances(request);

                for (Reservation reservation : response.reservations()) {
                    for (Instance instance : reservation.instances()) {
                        System.out.println("\nInstance Id is " + instance.instanceId()+" | Image id is " + instance.imageId()+" | Instance type is " + instance.instanceType()+" | Instance state is " + instance.state().name()+"| monitoring information is " + instance.monitoring().state());
                        System.out.println("Instance DNS name is " + instance.privateDnsName()+"| Instance Key name is "+instance.keyName()+"| Instance Group name is"+instance.securityGroups()+"| Instance public Ip is"+instance.publicIpAddress());
                    }
                }
                nextToken = response.nextToken();
            } while (nextToken != null);

        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);
        }
    }
    private static Instance instancerequest(Ec2Client ec2,Filter filter) {
        DescribeInstancesRequest request = DescribeInstancesRequest.builder().filters(filter).maxResults(6).build();
        DescribeInstancesResponse response = ec2.describeInstances(request);
        Instance instance =response.reservations().get(0).instances().get(0);
        return instance;
    }

    public static String instanceState(Ec2Client ec2,String instance_id) {
        Instance instance=null;
        try {
            Filter filter = Filter.builder().name("instance-id").values(instance_id).build();
            instance = instancerequest(ec2,filter);
            System.out.println("Instance state is " + instance.state().name().toString());
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);
        }
        return instance.state().name().toString();
    }


    public static String instanceName(Ec2Client ec2,String instance_id) {
        Instance instance=null;
        try {
//            Filter filter = Filter.builder().name("instance-state-name").values("running").build();
            Filter filter = Filter.builder().name("instance-id").values(instance_id).build();
        instance = instancerequest(ec2,filter);
            System.out.println("instance name is "+instance.tags().get(2).value());

        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);
        }
        return instance.tags().get(2).value();
    }

    public static String instanceType(Ec2Client ec2,String instance_id) {
        Instance instance=null;
        try {
            Filter filter = Filter.builder().name("instance-id").values(instance_id).build();
            instance = instancerequest(ec2,filter);
            System.out.println("instance type is "+instance.instanceType().toString());

        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);
        }
        return instance.instanceType().toString();
    }
    public static String instanceId(Ec2Client ec2,String instance_name) {
        Instance instance=null;
        try {
            Filter filter = Filter.builder().name("tag:Name").values(instance_name).build();
            instance = instancerequest(ec2,filter);
            System.out.println("Instance Id is " + instance.instanceId());
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);
        }
        return instance.instanceId();
    }


    public static List<String> describeEC2Instance(Ec2Client ec2,String Tag, String Name) {

        ArrayList<String> PublicIPs = new ArrayList<>();
        try {
            Filter filter = Filter.builder().name(Tag).values(Name).build();
            DescribeInstancesRequest request = DescribeInstancesRequest.builder().filters(filter).maxResults(6).build();
            DescribeInstancesResponse response = ec2.describeInstances(request);
            for (Reservation reservation : response.reservations()) {
                for (Instance instance : reservation.instances()) {
                    System.out.println("Instance IP is " + instance.publicIpAddress());
                    PublicIPs.add(instance.publicIpAddress());

                }
            }
    } catch (Ec2Exception e) {
        System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);
    }
        System.out.println("IPs: " + PublicIPs);
        return PublicIPs;
    }
    @SneakyThrows
    public static void stopInstance(Ec2Client ec2, String instance_id) {
        try {
            StopInstancesRequest request = StopInstancesRequest.builder().instanceIds(instance_id).build();
            ec2.stopInstances(request);
        }catch (Ec2Exception e){System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);}
        TimeUnit.SECONDS.sleep(60);
    }

    @SneakyThrows
    public static void rebootInstance(Ec2Client ec2, String instance_id) {
        try {
            RebootInstancesRequest request = RebootInstancesRequest.builder().instanceIds(instance_id).build();
        ec2.rebootInstances(request);
        }catch (Ec2Exception e){System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);}
        TimeUnit.SECONDS.sleep(60);
    }

    @SneakyThrows
    public static void startInstance(Ec2Client ec2, String instance_id) {
        try {
            StartInstancesRequest request = StartInstancesRequest.builder().instanceIds(instance_id).build();
        ec2.startInstances(request);
        }catch (Ec2Exception e){System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);}
        TimeUnit.SECONDS.sleep(60);
    }

    public static void terminateInstance( Ec2Client ec2, String instance_id) {

        try{
            TerminateInstancesRequest request = TerminateInstancesRequest.builder().instanceIds(instance_id).build();
            ec2.terminateInstances(request);
        } catch (Ec2Exception e) {System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);}
    }

    public static void describeEC2Address(Ec2Client ec2 ) {

        try {
            DescribeAddressesResponse response = ec2.describeAddresses();
            for(Address address : response.addresses()) {
                System.out.printf(
                        "Found address with public IP %s, "+"domain %s, " +"allocation id %s " +"and NIC id %s \n",
                        address.publicIp(), address.domain(), address.allocationId(), address.networkInterfaceId());
            }
        } catch (Ec2Exception e) {System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);}
    }
    public static void disassociateAddress(Ec2Client ec2, String associationId) {
        try {
            DisassociateAddressRequest request = DisassociateAddressRequest.builder().associationId(associationId).build();
            ec2.disassociateAddress(request);
            System.out.println("You successfully disassociated the address!");
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);}
    }

    public static void changeInstanceType(Ec2Client ec2, String instanceId) {
        try {
            ModifyInstanceAttributeRequest request = ModifyInstanceAttributeRequest.builder()
                    .instanceId(instanceId)
                    .instanceType(AttributeValue.builder().value("t2.nano").build())
                    .build();
            ec2.modifyInstanceAttribute(request);
            System.out.println("You successfully changed instance type!");
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);}
    }
    public static String associateAddress(Ec2Client ec2, String instanceId, String allocationId) {
        try {
            AssociateAddressRequest associateRequest = AssociateAddressRequest.builder().instanceId(instanceId).allocationId(allocationId).build();
            AssociateAddressResponse associateResponse = ec2.associateAddress(associateRequest);
            return associateResponse.associationId();
        } catch (Ec2Exception e) {System.err.println(e.awsErrorDetails().errorMessage());System.exit(1);
        }
        return "";
    }

    public static void availabilityZone(Ec2Client ec2) {
        DescribeAvailabilityZonesResponse zonesResponse = ec2.describeAvailabilityZones();
        for (AvailabilityZone zone : zonesResponse.availabilityZones()) {
            System.out.printf(
                    "Found Availability Zone %s " +
                            "with status %s " +
                            "in region %s",
                    zone.zoneName(),
                    zone.state(),
                    zone.regionName());
            System.out.println();
        }
    }

}
