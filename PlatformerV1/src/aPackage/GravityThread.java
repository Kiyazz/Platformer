package aPackage;

public class GravityThread extends Thread {
	
	public static boolean jumped;
	public static int playerDownSpeed = 0;
	
	private static GravityThread gravityInstance = new GravityThread();
	
	private GravityThread() {
		super(null, null, "Gravity");
	}
	/**
	 * makes the player jump
	 */
	public static void jump() {
		playerDownSpeed = -Constants.JUMP_SPEED;
	}
	
	@Override
	public void run() {
		while (true) {
			if (!Driver.isCutsceneActive() && !NPC.speaking) {
			
				if (Driver.isSpaceClicked() && Driver.isGrounded) {
					playerDownSpeed = -Constants.JUMP_SPEED;
					Driver.isGrounded = false;
					jumped = true;
				}
				
				if (!Driver.isGrounded && playerDownSpeed < Constants.TERMINAL_VELOCITY) {
					playerDownSpeed += 1;
					
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
				if (Level.list.size() != 0)
				Platform.updateGroundedAll(Level.list.get(Level.level));
				}
				catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
			Platform.roofCollisionAll(Level.getactiveLevel());
		}
	}
	/**
	 * Get the one instance of GravityThread
	 * @return
	 */
	public static GravityThread getInstance() {
		return gravityInstance;
	}

}
