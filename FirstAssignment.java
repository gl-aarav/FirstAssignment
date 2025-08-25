/**
 *	FirstAssignment.java
 *	Display a brief description of your summer vacation on the screen.
 *
 *	Compile: javac -cp .:myAcm.jar FirstAssignment.java
 *  Run: java -cp .:myAcm.jar FirstAssignment
 *
 *	@author	Aarav
 *	@since	8/25/2025
 */
import java.awt.Font;
import acm.program.GraphicsProgram;
import acm.graphics.GLabel;

public class FirstAssignment extends GraphicsProgram 
{

    public void run() 
    {
        Font f = new Font("Serif", Font.BOLD, 18);
        double x = 10;
        double y = 30;
        
        String[] lines =
        	{
        	    "What I did on my summer vacation ...",
        	    "This summer, I spent time learning both machine learning and physics concepts.",
        	    "In machine learning, I explored datasets, studied models, and ran small experiments.",
        	    "I also reviewed physics topics like mechanics, energy, and simple experiments at home.",
        	    "Combining both subjects was exciting and helped me understand patterns in the world.",
        	    "During my break, my family and I went on a trip to relax and enjoy nature's beauty.",
        	    "We visited Mount Shasta, admired its towering peaks, and hiked through scenic trails.",
        	    "The fresh mountain air and stunning views made our adventure refreshing and peaceful.",
        	    "At Crater Lake, I marveled at the deep blue water reflecting the surrounding cliffs.",
        	    "We explored Burney Falls and other cascades, enjoying the soothing sound of water.",
        	    "The natural landscapes inspired me to think about physics in real-world contexts.",
        	    "I also spent quiet mornings practicing problems in physics and coding projects in ML.",
        	    "Combining outdoor adventures with learning made the summer both fun and productive.",
        	    "After returning home, I felt energized and ready to continue my studies with focus.",
        	    "Overall, this summer was a balance of exploration, learning, and family experiences.",
        	    "I look forward to applying what I've learned in the upcoming years!"
        	};


        for (int i = 0; i < lines.length; i++) 
        {
            GLabel label = new GLabel(lines[i], x, y);
            label.setFont(f);
            add(label);
            y += label.getHeight() + 10;
        }
    }

    public static void main(String[] args) 
    {
        new FirstAssignment().start(args);
    }
}
