
import java.io.File;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {
    private static final int DETECT_MODE = 0;
    private static final int SML_MODE = 1;
    private static final int XML_MODE = 2;

    public static final void main(String[] args)
        throws Exception {
        int mode = DETECT_MODE;
        File input = null;
        File output = null;
        for(int i=0; i<args.length; ++i){
            if(args[i].equals("-sml")){
                mode = SML_MODE;
            }else if(args[i].equals("-xml")){
                mode = XML_MODE;
            }else if(args[i].equals("-h") || args[i].equals("--help")){
                usage(System.out);
                System.exit(0);
            }else if(input == null){
                input = new File(args[i]);
            }else if(output == null){
                output = new File(args[i]);
            }else{
                usage(System.err);
                System.exit(-1);
            }
        }
        if(mode == DETECT_MODE){
            if(input == null){
                System.err.println("error: need either input file or input mode");
                usage(System.err);
                System.exit(-1);
            }
            if(input.getName().toLowerCase().endsWith(".sml")){
                mode = SML_MODE;
            }else if(input.getName().toLowerCase().endsWith(".xml")){
                mode = XML_MODE;
            }else{
                System.err.println("error: cannot determine mode for file "+input.getName());
                System.exit(-1);
            }
        }
        InputStream in = input == null || input.getName().equals("-") ? 
            System.in : new FileInputStream(input);
        PrintStream out = output == null || output.getName().equals("-") ? 
            System.out : new PrintStream(new FileOutputStream(output));
        if(mode == SML_MODE){
            SmlParser parser = new SmlParser(in);
            parser.setOutputStream(out);
            parser.Document();
        }else{
            XmlParser parser = new XmlParser(in);
            parser.setOutputStream(out);
            parser.Document();
        }
    }

    public static final void usage(PrintStream out){
        out.println(Main.class.getName()+" [options] [infile [outfile]]");
        out.println("options:");
        out.println("\t -sml\t\t SML input mode");
        out.println("\t -xml\t\t XML input mode");
        out.println("\t -h\t\t print this help message");
        out.println("by default the program tries to determine the input file type by suffix.");
        out.println("either or both filenames may be '-' for std in/out.");
    }
}