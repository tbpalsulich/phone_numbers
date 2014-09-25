import org.apache.tika.example.PhoneExtractingContentHandler;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrabPhoneNumbers {
    private static ArrayList<String> phoneNumbers = new ArrayList<String>();
    private static int failedFiles, successfulFiles = 0;

    public static void main(String[] args){
        if (args.length != 1) {
            System.err.println("Usage `java GrabPhoneNumbers [corpus]");
        }
        final File folder = new File(args[0]);
        processFolder(folder);
        System.out.println(phoneNumbers.toString());
        System.out.println("Parsed " + successfulFiles + "/" + (successfulFiles + failedFiles));
    }

    public static void processFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                processFolder(fileEntry);
            } else {
                try {
                    process(fileEntry);
                    successfulFiles++;
                } catch (Exception e) {
                    failedFiles++;
                    // Ignore this file...
                }
            }
        }
    }

    public static void process(File file) throws Exception {
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        // The PhoneExtractingContentHandler will examine any characters for phone numbers before passing them
        // to the underlying Handler.
        PhoneExtractingContentHandler handler = new PhoneExtractingContentHandler(new BodyContentHandler(), metadata);
        InputStream stream = new FileInputStream(file);
        try {
            parser.parse(stream, handler, metadata, new ParseContext());
        }
        finally {
            stream.close();
        }
        String[] numbers = metadata.getValues("phonenumbers");
        for (String number : numbers) {
            phoneNumbers.add(number.substring(number.length() - 10));
        }
    }
}
