import java.io.*;

/**
 * Created by Jake Finnegan on 2/4/2017.
 */
public class MakingArffFile {

    static double percentGoingToGym;
    static double numberOfAttrs;

    public static String getDay(double random) {
        numberOfAttrs++;
        if (random <= 2./7) {
            percentGoingToGym += 0.7;
            return "weekend, ";
        }       else if (random <= 4./7) {
            percentGoingToGym += 0.93;
            return "ttr, ";
        }       else {
            percentGoingToGym += 0.83;
            return "mwf, ";
        }
    }

    public static String getTemp(double random) {
        double tempRandom = random*2 - 1;
        int temperature = (int) (tempRandom*40 + 60);

        String isIcy = "FALSE, ";

        double icyRandom = Math.random();
        if (temperature <= 32 && icyRandom < 0.15) {
            isIcy = "TRUE, ";
            numberOfAttrs += 3;
        }
//      } else {
//            percentGoingToGym += 0.3 + 0.5*random;
//        }

        return (temperature) + ", " + isIcy;
    }

    public static double[] getRandoms() {
        return new double[] {
                Math.random(),
                Math.random(),
                Math.random(),
                Math.random()
        };
    }

    public static String isSick(double random) {
        if (random >= .985) {
            numberOfAttrs += 12;
            return "TRUE, ";
        } else {
//            percentGoingToGym += 0.35 - Math.random()*0.05;
            return "FALSE, ";
        }
    }

    public static String isSore(double random) {
        if (random >= 0.9) {
            numberOfAttrs += 0.5;
//            percentGoingToGym += (0.05 - (Math.random()*0.04));
            return "TRUE, ";
        } else {
//            percentGoingToGym += 0.04 + Math.random()*0.01;
            return "FALSE, ";
        }
    }

    public static void main (String[] args) {

        try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("goToGymLarge.arff"), "utf-8"))) {

            writer.write("@relation gym\n\n");

            writer.write("@attribute temperature real\n");
            writer.write("@attribute icey {TRUE, FALSE}\n");
            writer.write("@attribute dayoftheweek { weekend, mwf, ttr}\n");
            writer.write("@attribute sick {TRUE, FALSE}\n");
            writer.write("@attribute sore {TRUE, FALSE}\n");
            writer.write("@attribute gotogym {yes, no}\n\n\n");

            writer.write("@data\n");

            double[] randoms;
            StringBuffer line;
            int went = 0;
            for(int i = 0; i < 10000; i++) {

                percentGoingToGym = 0.0;
                numberOfAttrs = 0;

                randoms = getRandoms();

                line = new StringBuffer();
                line.append(getTemp(randoms[0]));
                line.append(getDay(randoms[1]));
                line.append(isSick(randoms[2]));
                line.append(isSore(randoms[3]));


               // if (Math.random()+0.07 <= percentGoingToGym) {
                if (Math.random() <= (percentGoingToGym/numberOfAttrs)) {
                    went++;
                    line.append("yes");
                } else {
                    line.append("no");
                }

                line.append("\n");
                writer.write(line.toString());
            }

            System.out.println("");
            System.out.print("Percentage Attended Gym: " + went/10000.*100);

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (Exception e) {
            System.out.println("Something Terrible Happened, Error: \n" + e.getMessage());
        }

    }
}
