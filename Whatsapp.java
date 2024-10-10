import java.util.Scanner;

public class Whatsapp {
    Sms sms[][];
    String contact[];
    static int counter[];
    static int contCount = 0;

    Whatsapp() {
        contact = new String[100];
        sms = new Sms[50][100];
        counter = new int[50];
    }

    public void addContact() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to add a new contact (y/n)");
        String choice = sc.nextLine();
        while (choice.equalsIgnoreCase("y")) {
            System.out.println("Enter number which you want to add");
            contact[contCount] = sc.nextLine().trim();;
            contCount++;
            System.out.println("contact added successfully");
            System.out.println("Do you want to add another contact");
             choice = sc.nextLine();
        }

    }

    public void dltContact() {
        Scanner s1 = new Scanner(System.in);

        System.out.println("Enter number which you want to delete");
        String contacttobedlt = s1.nextLine();
        if (findContact(contacttobedlt)) {
            int index = getIndex(contacttobedlt);
            for (int i = index; i < contCount - 1; i++) {
                contact[i] = contact[i + 1];
                contact[contCount- 1] = null;
            }
            contCount--;
            System.out.println("contact deleted successfully");
        } else {
            System.out.println("Contact not found, cannot delete.");
        }

    }

    public void displayContacts() {
        for (String c : contact) {
            if (c != null) {
                System.out.println(c);
            }

        }
        System.out.println("contact displayed successfully");
        System.out.printf("\n");
    }

    public boolean findContact(String receiver) {
        receiver=receiver.trim();
        for (int i = 0; i < contCount; i++) {
            if (contact[i].equals(receiver)) {
                return true;
            }
        }
        return false;
    }

    public int getIndex(String receiver) {
        receiver=receiver.trim();
        for (int i = 0; i < contCount; i++) {
            if (contact[i].equals(receiver)) {
                // 50 wala match hua
                return i;
                // 50
            }
        }
        return -1;
    }

    public void sendMsg() {

        Scanner input = new Scanner(System.in);

        // Sms(String sender, String receiver, String content, String msgId, boolean
        // status)
        System.out.println("Do you want to send a new msg?(y/n)");
        String choice = input.nextLine();
        while (choice.equalsIgnoreCase("y")) {
            System.out.println("write receiver Id/no. (start with +92 and max character 13 e.g. +923099113913):");
            String receiver = input.nextLine();
            if (findContact(receiver)) {
                int contactIndex = getIndex(receiver);
                if (contactIndex == -1) {
                    System.out.println("Cannot find such contact");
                    break;
                }
                String sender = "you";
                System.out.println("What do you want to write :");
                String content = input.nextLine();

                int msgId = counter[contactIndex] + 1;
                System.out.println("your msg id is  :" + msgId);
                System.out.println("setting  msg status");
                boolean status = false;

                sms[contactIndex][counter[contactIndex]] = new Sms(sender, receiver, content, msgId, status);
                // sms[0][0]
                // sms[0][1]
                // sms[0][2]
                counter[contactIndex]++;
                System.out.println("Message sent to " + receiver + " with ID: " + msgId);
            } else {
                System.out.println("cannot find such contact");
            }
            System.out.println("do you want to send another");
            choice = input.nextLine();
        }

    }

    public void checkStatus() {
        Scanner sc = new Scanner(System.in);
        System.out.println("write number to check if the message is seen or not");
        String number = sc.nextLine();
        
        if (findContact(number)) {
            int numIndex = getIndex(number);
            
            boolean allSeen = true;  // Assume all messages are seen initially
            
            for (int i = 0; i < counter[numIndex]; i++) {
                if (!sms[numIndex][i].isStatus()) {
                    allSeen = false;  // If any message is not seen, change the flag
                    break;
                }
            }
            
            if (allSeen) {
                System.out.println("All messages are seen for " + number);
            } else {
                System.out.println("Some messages are not seen for " + number);
            }
        } else {
            System.out.println("No contact found");
        }
    }
    public void sortMsg(){
        Scanner in = new Scanner(System.in);
        System.out.println("write your number to sort msgs");
        String receiverId = in.nextLine().trim();
        int contactIndex = getIndex(receiverId);
        if (contactIndex==-1) {
            System.out.println("can't find such contact");
            return;
        }

        System.out.println(counter[contactIndex]+" msgs for this number");
        if (counter[contactIndex] == 0) {
            System.out.println("No messages found for this number.");
            return;
        }
        for (int i = counter[contactIndex]-1; i >= 0; i--) {
            if (sms[contactIndex][i] != null)  {
              //  System.out.println(counter[contactIndex]+" msgs for this number");
                System.out.println(sms[contactIndex][i].getTimestamp()+"-"+sms[contactIndex][i].getContent());
            }else{
                System.out.println("no msgs found");
            }
        }
    }
    public void displaymsg() {
        Scanner in = new Scanner(System.in);
        boolean msgCheck = false;
        System.out.println("write your number to see msg");
        String receiverId = in.nextLine();
        int contactIndex = getIndex(receiverId);
        if (contactIndex==-1) {
            System.out.println("can't find such contact");
            return;
        }
        System.out.println(counter[contactIndex]+" msg for this number");
        for (int i = 0; i < counter[contactIndex]; i++) {
           
            System.out.println(sms[contactIndex][i].toString());
            System.out.print("\n");
            sms[contactIndex][i].setStatus(true);
            msgCheck = true;

        }
        if (!msgCheck) {
            System.out.println("Sorry! can't find any message for " + receiverId + " id");
        }
    }

    public void dltMsg() {
        Scanner input = new Scanner(System.in);
        System.out.println("write your id/no. to dlt msg");
        String receiverId = input.nextLine();
        int contactIndex = getIndex(receiverId);
        boolean dltCheck = false;
        System.out.println("there are "+counter[contactIndex]+"msgs available for this number");
        for (int i = 0; i < counter[contactIndex]; i++) {
          
            System.out.println("Msgid: " + sms[contactIndex][i].getMsgId() + "," + sms[contactIndex][i].getContent());
        }
        System.out.println("select msg Id to delete");
        int msgtodlt = input.nextInt();

        for (int i = 0; i < counter[contactIndex]; i++) {
            if (sms[contactIndex][i].getMsgId() == msgtodlt) {
                for (int j = i; j < counter[contactIndex] - 1; j++) {
                    sms[contactIndex][j] = sms[contactIndex][j + 1];
                }
                sms[contactIndex][counter[contactIndex] - 1] = null;
                counter[contactIndex]--;
                dltCheck = true;
                break;
            }
        }
        if (dltCheck) {
            System.out.println("msg successfully deleted with id: " + msgtodlt);
        } else {
            System.out.println("no msg found with this id");
        }

    }
}
