//Agente Gradient Descent linear 
package AgenteGDL;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import jade.core.behaviours.OneShotBehaviour;
import javax.swing.JOptionPane;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import jade.core.Agent;
import java.util.List;

public class Agente extends Agent {

    private static final double epsilon = Double.MIN_VALUE;
    
    public void setup () {
        System.out.println("Hola, soy el agente "+getLocalName()+", comenzamos:");
        List<Point2D> data = Datos();
        double alpha = 0.01;
        int maxIterations = 10_000;
        Point2D finalTheta = singleVarGradientDescent(data, 0.1, 0.1, alpha, maxIterations);
        System.out.printf("theta0 = %f, theta1 = %f", finalTheta.getX(), finalTheta.getY());
        System.out.println("");
    }

    private Point2D singleVarGradientDescent(List<Point2D> data, double initialTheta0, double initialTheta1, double alpha, int maxIterations) {
        double theta0 = initialTheta0, theta1 = initialTheta1;
        double oldTheta0 = 0, oldTheta1 = 0;

        for (int i = 0 ; i < maxIterations; i++) {
            if (hasConverged(oldTheta0, theta0) && hasConverged(oldTheta1, theta1))
                break;

            oldTheta0 = theta0;
            oldTheta1 = theta1;

            theta0 = theta0 - (alpha * gradientofThetaN(theta0, theta1, data, x -> 1.0));
            theta1 = theta1 - (alpha * gradientofThetaN(theta0, theta1, data, x -> x));
        }
        return new Point2D.Double(theta0, theta1);
    }

    private boolean hasConverged(double old, double current) {
        return (current - old) < epsilon;
    }

    private double gradientofThetaN(double theta0, double theta1, List<Point2D> data, DoubleUnaryOperator factor) {
        double m = data.size();
        return (1.0 / m) * sigma(data, (x, y) ->  (hypothesis(theta0, theta1, x) - y) * factor.applyAsDouble(x));
    }

    private double hypothesis(double theta0, double theta1, double x) {
        return theta0 + (theta1 * x);
    }

    private double sigma(List<Point2D> data, DoubleBinaryOperator inner) {
        return data.stream()
                   .mapToDouble(theta -> {
                       double x = theta.getX(), y = theta.getY();
                       return inner.applyAsDouble(x, y);
                   })
                   .sum();
    }

    private List<Point2D> Datos() {
        double n;
        List<Point2D> data = new ArrayList<>();
        data.add(new Point2D.Double(23,651));
        data.add(new Point2D.Double(26,762));
        data.add(new Point2D.Double(30,856));
        data.add(new Point2D.Double(34,1063));
        data.add(new Point2D.Double(43,1190));
        data.add(new Point2D.Double(48,1298));
        data.add(new Point2D.Double(52,1421));
        data.add(new Point2D.Double(57,1440));
        data.add(new Point2D.Double(58,1518));      
        /*System.out.println("Para finalizar introduce 0 en ambas ");
        do {
            n= Double.parseDouble(JOptionPane.showInputDialog (null,"Introduce X: ",
        "Agente X",JOptionPane.QUESTION_MESSAGE));
            if (n != 0){
                data.add(new Point2D.Double(n,n));
            }
            n= Double.parseDouble(JOptionPane.showInputDialog (null,"Introduce Y: ",
        "Agente Y",JOptionPane.QUESTION_MESSAGE));
            if (n != 0){
                data.add(new Point2D.Double(n,n));
            }
        }while (n != 0);*/
        return data;
    }
    
}
