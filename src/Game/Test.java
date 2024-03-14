package Game;

public class Test {
    public static void main(String[] args) {
        int i = 0;
        while (i < 200) {
            System.out.println(i + "...................");
            Shape shape = new Shape();
            shape.setShape();
            for (int j = 0; j < shape.getRow(); j++) {
                for (int j2 = 0; j2 < shape.getColumn(); j2++) {
                    if (shape.getShape()[j][j2])
                        System.out.print("* ");
                    else 
                        System.out.print("  ");
                }
                System.out.println();
            }
            i++;
    
    }
    
    
    // while (i < 200) {
    //     Shape shape = new Shape();
    //     shape.setShape();
    //     i++;
    // }
        
    }


}