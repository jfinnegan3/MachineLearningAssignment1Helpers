import java.io.*;

/**
 * Created by Jake Finnegan on 2/4/2017.
 */
public class MakingArffFile {

    static double percentGoingToGym;

    public static String getDay(double random) {
        if (random <= 2./7) {
            percentGoingToGym += 0.7*Math.random()*0.40;
            return "weekend, ";
        }       else if (random <= 4./7) {
            percentGoingToGym += 0.95*Math.random()*0.40;
            return "ttr, ";
        }       else {
            percentGoingToGym += 0.85*Math.random()*0.40;
            return "mwf, ";
        }
    }

    public static String getTemp(double random) {
        double tempRandom = random*2 - 1;
        tempRandom = (tempRandom*40 + 60);

        percentGoingToGym += random * 0.02;

        String isIcy = "FALSE, ";

        double icyRandom = Math.random();
        if (tempRandom <= 32 && icyRandom < 0.2) {
            isIcy = "TRUE, ";
            percentGoingToGym += 0.25 - Math.random()*0.22;
        } else {
            percentGoingToGym += 0.20 + 0.3*Math.random();
        }

        return ((int) tempRandom) + ", " + isIcy;
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
            percentGoingToGym += 0.3 - Math.random()*0.28;
            return "TRUE, ";
        } else {
            percentGoingToGym += 0.28 + Math.random()*0.02;
            return "FALSE, ";
        }
    }

    public static String isSore(double random) {
        if (random >= 0.9) {
            percentGoingToGym += 0.05 - Math.random()*0.04;
            return "TRUE, ";
        } else {
            percentGoingToGym += 0.04 + Math.random()*0.01;
            return "FALSE, ";
        }
    }

//    public static String isGoingToGym(double[] randoms) {
//        double percentGoingToGym = 0.0;
//        percentGoingToGym += randoms[0]*0.02;
//
//        if (randoms[1] <= 2./7)
//            percentGoingToGym += 0.6*Math.random()*0.48;
//        else if (randoms[1] <= 4./7)
//            percentGoingToGym += 0.95*Math.random()*0.48;
//        else
//            percentGoingToGym += 0.8*Math.random()*0.48;
//
//        if (randoms[2] <= 0.985)
//            percentGoingToGym += 0.3 - Math.random()*0.1;
//        else
//            percentGoingToGym +=  Math.random()*0.1;
//
//        if (randoms[3] <= .9)
//            percentGoingToGym += 0.2 - Math.random()*0.05;
//        else
//            percentGoingToGym += Math.random()*0.195;
//
//        double temp = Math.random();
//
//        return temp < percentGoingToGym ? "yes" : "no";
//
//    }

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
            double goingToGym;
            int went = 0;
            for(int i = 0; i < 10000; i++) {

                percentGoingToGym = 0.0;

                randoms = getRandoms();

                line = new StringBuffer();
                line.append(getTemp(randoms[0]));
                line.append(getDay(randoms[1]));
                line.append(isSick(randoms[2]));
                line.append(isSore(randoms[3]));


                if (Math.random() <= percentGoingToGym) {
                    line.append("yes");
                } else {
                    line.append("no");
                }

                line.append("\n");
                writer.write(line.toString());
            }

            System.out.println("");
            System.out.print("Percentage Attended Gym: " + went/10000.);

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (Exception e) {
            System.out.println("Something Terrible Happened, Error: \n" + e.getMessage());
        }

    }
}
