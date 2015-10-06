

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user0001
 */
public class Exercise16 {

    private final String prompt = ">>> ";
    private final String mailFromHeader = "MAIL FROM";
    private final String rcptFromHeader = "RCPT TO";
    private final String dataHeader = "DATA";
    private final String quitCommand = "QUIT";
    private final String dataContentTerminator = ".";
    private final String headerTerminator = ":";
    private final String ok = "OK";
    //
    private final String invalidCommand = "Invalid command.";
    private final int indexNotFound = -1;
    //
    private final int emailTokensCount = 2;
    private final String emailSplitToken = "@";
    private final String invalidEmail = "Invalid email address.";
    //
    private String mailContent;
    private String rcptContent;
    private String dataContent = new String();
    //
    private final String fromLineFormat = "from: %s";
    private final String toLineFormat = "to: %s";

    public static void main(String[] args) {
        new Exercise16().mainMethod();
    }

    private void mainMethod() {

        String enteredString;
        //
        //  MAIL FROM:
        //
        while (true) {

            enteredString = readInput();
            if (!validateMsgHeader(enteredString, mailFromHeader)) {
                showInvalidCommand();
                continue;
            }
            String enteredMessage = getMsgContent(enteredString, mailFromHeader);

            if (validateEmailAddress(enteredMessage)) {
                mailContent = enteredMessage;
                showOkStatus();
                break;
            } else {
                showInvalidEmail();
            }

        }
        //
        //  RCPT TO:
        //
        while (true) {

            enteredString = readInput();
            if (!validateMsgHeader(enteredString, rcptFromHeader)) {
                showInvalidCommand();
                continue;
            }
            String enteredMessage = getMsgContent(enteredString, rcptFromHeader);
            if (validateEmailAddress(enteredMessage)) {
                rcptContent = enteredMessage;
                showOkStatus();
                break;
            } else {
                showInvalidEmail();
            }
        }
        //
        //  DATA:
        //
        while (true) {

            enteredString = readInput();
            if (!validateMsgHeader(enteredString, dataHeader)) {
                showInvalidCommand();
                continue;
            }
            String enteredMessage = getMsgContent(enteredString, dataHeader);
            while (!dataContentTerminator.equals(enteredMessage)) {
                dataContent +=  enteredMessage + "\n";
                enteredMessage = System.console().readLine();
            }
            break;
        }
        //
        //  Print email
        //
        System.out.println("Sending email...");
        System.out.println(String.format(fromLineFormat, mailContent));
        System.out.println(String.format(toLineFormat, rcptContent));
        System.out.print(dataContent);
        //
        System.out.println("...done");

    }
    //
    //  Helper methods
    //
    private String readInput() {

        System.out.print(prompt);
        String enteredString = System.console().readLine();
        checkForQuit(enteredString);
        return enteredString;
    }

    private void checkForQuit(String message) {

        if (message != null && message.startsWith(quitCommand)) {
            System.exit(0);
        }
    }

    private boolean validateMsgHeader(String message, String header) {

        if (message == null || header == null) {
            return false;
        }
        return message.startsWith(header + headerTerminator);
    }

    private boolean validateEmailAddress(String message) {

        if (message == null) {
            return false;
        }
        String[] tokens = message.split(emailSplitToken);
        return (tokens.length == emailTokensCount && !tokens[0].isEmpty());
    }

    private void showInvalidCommand() {

        System.out.println(invalidCommand);
    }

    private void showInvalidEmail() {

        System.out.println(invalidEmail);
    }

    private void showOkStatus() {

        System.out.println(ok);
    }

    private String getMsgContent(String message, String header) {

        if (message == null || header == null) {
            return null;
        }
        header += headerTerminator;
        int index = message.indexOf(header);
        if (index == indexNotFound) {
            return null;
        }
        return message.substring(index + header.length()).trim();
    }
}
