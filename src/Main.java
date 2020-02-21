import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String netBitRateCSV = "netbitrate.csv";
        String bandWidthCSV = "bandwidth.csv";
        BufferedReader brNBR = null;
        BufferedReader brBW = null;
        String lineNBR = null;
        String lineBW = null;
        String csvSplit = ",";
        String[] termsNBR;
        String[] termsBW;
        float networkBitRate;
        float bandwith;
        float nbu;

        List<String> bandwithLines;

        try {
            bandwithLines = new ArrayList<String>();
            brNBR = new BufferedReader(new FileReader(netBitRateCSV));
            brBW = new BufferedReader(new FileReader(bandWidthCSV));
            lineNBR = brNBR.readLine();
            lineBW = brBW.readLine();

            System.out.println("Timestamp \t \t \t| Server  | Network Interface | Network bit rate / Bandwidth");

            while( lineBW != null){// collects each line of the bw file
                lineBW = brBW.readLine();
                bandwithLines.add(lineBW);
            }

            //I am trying to figure out for each line of NBR file, what is the network bandwith utilization

            // number one i have to trawl through each line of NBR, and find the equivalent server and interface
            //that corresponds to the exact server and interface in BW

            while( lineNBR != null ){
                lineNBR = brNBR.readLine();
                if(lineNBR == null){
                    break;
                }
                termsNBR = lineNBR.split(csvSplit);

                int length = bandwithLines.size() - 1;
                int k = 0;

                while(k < length){
                    String element= bandwithLines.get(k);
                    termsBW = element.split(csvSplit);

                    if(termsNBR[1].equals(termsBW[0]) && termsNBR[2].equals(termsBW[1])){
                        networkBitRate = Float.parseFloat(termsNBR[3]);
                        bandwith = Float.parseFloat(termsBW[2]);
                        nbu = networkBitRate / bandwith;
                        System.out.println(termsNBR[0] + " | " + termsNBR[1] + " | " + termsNBR[2] + " \t \t \t  | " + nbu);
                    }
                    k++;
                }
            }

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (brNBR != null) {
                try {
                    brNBR.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (brBW != null) {
                try {
                    brBW.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
