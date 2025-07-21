public class BoardPositions {
	public final int[][] positions = {
			{375, 35},   // Position 1 PURPLE*
			{465, 55},   // Position 2 *
			{545, 80},   // Position 3 *
			{615, 128},  // Position 4 *
			{668, 198},  // Position 5 ORANGE*
			{700, 283},  // Position 6 *
			{715, 361}, // Position 7 *
			{702, 448}, // Position 8 *
			{665, 523}, // Position 9 BLUE
			{610, 595}, // Position 10
			{545, 643}, // Position 11
			{462, 676}, // Position 12 *
			{375, 688}, // Position 13 GREEN
			{288, 680}, // Position 14
			{206, 642}, // Position 15
			{133, 592}, // Position 16
			{81, 525}, // Position 17 PINK
			{48, 445}, // Position 18
			{39, 360}, // Position 19
			{46, 278}, // Position 20
			{83, 200}, // Position 21 YELLOW
			{134, 135}, // Position 22
			{205, 81}, // Position 23
			{291, 51}, // Position 24

	};
	
	public int getPositionX(int index) {
        if (index >= 0 && index < positions.length) {
            return positions[index][0];
        }
        return -1; // Return a default value or handle the error as needed
    }

    public int getPositionY(int index) {
        if (index >= 0 && index < positions.length) {
            return positions[index][1];
        }
        return -1; // Return a default value or handle the error as needed
    }

}


