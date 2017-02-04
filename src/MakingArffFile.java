import java.io.*;

/**
 * Created by Jake Finnegan on 2/4/2017.
 */
public class MakingArffFile {

    public static String getDay(double random) {
        if (random <= 2./7)
            return "TRUE, FALSE, FALSE, ";
        else if (random <= 4./7)
            return "FALSE, FALSE, TRUE, ";
        else
            return "FALSE, TRUE, FALSE, ";
    }

    public static String getTemp(double random) {
        double tempRandom = random*2 - 1;
        return ((int) (tempRandom*30 + 60)) + ", ";
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
        return random >= .985 ? "TRUE, " : "FALSE, ";
    }

    public static String isSore(double random) {
        return random >= .90 ? "TRUE, " : "FALSE, ";
    }

    public static String isGoingToGym(double[] randoms) {
        double percentGoingToGym = 0.0;
        percentGoingToGym += randoms[0]*0.02;

        if (randoms[1] <= 2./7)
            percentGoingToGym += 0.6*Math.random()*0.48;
        else if (randoms[1] <= 4./7)
            percentGoingToGym += 0.95*Math.random()*0.48;
        else
            percentGoingToGym += 0.8*Math.random()*0.48;

        if (randoms[2] <= 0.985)
            percentGoingToGym += 0.3 - Math.random()*0.1;
        else
            percentGoingToGym +=  Math.random()*0.1;

        if (randoms[3] <= .9)
            percentGoingToGym += 0.2 - Math.random()*0.05;
        else
            percentGoingToGym += Math.random()*0.195;

        double temp = Math.random();

        return temp < percentGoingToGym ? "yes" : "no";

    }

    public static void main (String[] args) {

        try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("goToGymLarge.arff"), "utf-8"))) {

            writer.write("@relation gym\n\n");

            writer.write("@attribute temperature real\n");
            writer.write("@attribute weekend {TRUE, FALSE}\n");
            writer.write("@attribute monwedfri {TRUE, FALSE}\n");
            writer.write("@attribute tuesthurs {TRUE, FALSE}\n");
            writer.write("@attribute sick {TRUE, FALSE}\n");
            writer.write("@attribute sore {TRUE, FALSE}\n");
            writer.write("@attribute gotogym {yes, no}\n\n\n");

            writer.write("@data\n");


            double tempRandom;
            int temp;
            double[] randoms;
            StringBuffer line;
            double goingToGym;
            int went = 0;
            for(int i = 0; i < 10000; i++) {

                randoms = getRandoms();

                line = new StringBuffer();
                line.append(getTemp(randoms[0]));
                line.append(getDay(randoms[1]));
                line.append(isSick(randoms[2]));
                line.append(isSore(randoms[3]));

                String going = isGoingToGym(randoms);

                if (going.equals("yes"))
                    went++;

                line.append(going);

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
