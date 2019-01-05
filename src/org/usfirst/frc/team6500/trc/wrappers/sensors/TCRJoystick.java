package org.usfirst.frc.team6500.trc.wrappers.sensors;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Joystick;

/**
 * An extension of Joystick that supports bound tasks.
 * 
 * @author Bennett Petzold <bennettpetzold01@gmail.com>
 */
public class TCRJoystick extends Joystick {
	
	private int[][] buttonSets; //The inner array is a set of buttons to be checked for a task
	private boolean[][] conditionSets; //conditionSets[i] corresponds to buttonSets[i] and tasks[i]
	private Runnable[] tasks;
	
	/**
	 * Construct an instance of a TCRJoystick. The joystick index is the USB port on the driver's station.
	 * 
	 * @param port the joystick's port
	 */
	public TCRJoystick(int port) {
		super(port);
		
		//Constructs empty 0-length lists
		buttonSets = new int[0][];
		conditionSets = new boolean[0][];
		tasks = new Runnable[0];
	}
	
	/**
	 * Binds buttons and conditions to run a task if matching.
	 * 
	 * @param buttons the buttons monitored
	 * @param conditions the required button states
	 * @param task the Runnable task
	 */
	public void bind(int[] buttons, boolean[] conditions, Runnable task) {
		buttonBind(buttons);
		conditionBind(conditions);
		taskBind(task);
	}
	
	private void buttonBind(int[] buttons) 
	{
		int[][] nextButtonSets = new int[buttonSets.length + 1][];
		for(int i = 0; i < buttonSets.length; i++) 
		{
			nextButtonSets[i] = buttonSets[i];
		}
		nextButtonSets[buttonSets.length] = buttons;
		buttonSets = nextButtonSets;
	}
	
	private void conditionBind(boolean[] conditions)
	{
		boolean[][] nextConditionSets = new boolean[conditionSets.length + 1][];
		for(int i = 0; i < conditionSets.length; i++) 
		{
			nextConditionSets[i] = conditionSets[i];
		}
		nextConditionSets[conditionSets.length] = conditions;
		conditionSets = nextConditionSets;
	}
	
	private void taskBind(Runnable task) {
		Runnable[] nextTasks = new Runnable[tasks.length + 1];
		for(int i = 0; i < tasks.length; i++) {
			nextTasks[i] = tasks[i];
		}
		nextTasks[tasks.length] = task;
		tasks = nextTasks;
	}
	
	/**
	 * Checks the conditions for stored tasks and returns all tasks with conditions met.
	 * 
	 * @return the tasks to be run.
	 */
	public ArrayList<Runnable> findTasks() {
		ArrayList<Runnable> taskList = new ArrayList<>();
		
		for(int i = 0; i < buttonSets.length; i++) 
		{
			if(conditionsMet(i)) taskList.add(tasks[i]);
		}
		
		return taskList;
	}
	
	/**
	 * Checks if the states for each button in the array matches their conditions
	 * 
	 * @param i the outer array index for buttonSets and conditionSets
	 * @return if the button states match the conditions
	 */
	private boolean conditionsMet(int i) {
		int[] buttons = buttonSets[i];
		boolean[] conditions = conditionSets[i];
		
		for(int j = 0; j < buttons.length; j++)
		{
			if(getRawButton(buttons[j]) != conditions[j]) return false;
		}
		
		return true;
	}
}