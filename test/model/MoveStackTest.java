package model;

import model.Move;
import model.MoveStack;

import junit.framework.TestCase;

public class MoveStackTest extends TestCase{
	
	MoveStack ms;
	Move m, n;
	
	protected void setUp() throws Exception
	{
		ms = new MoveStack();
		m = new Move(Color.RED,Color.RED,Color.RED,Color.RED);
		ms.addMove(m);
		n = new Move(Color.BLUE,Color.BLUE,Color.BLUE,Color.BLUE);
		ms.addMove(n);		
	}
	
	public void testAddMoveAndTop()
	{
		assertEquals(ms.top(),n);
		Move top = new Move(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
		ms.addMove(top);
		assertEquals(ms.top(),top);
	}
	
	public void testUndoAndRedo()
	{
		//Test if you can undo from cursor = 1
		//You can totally do this!
		assertEquals(ms.top(),n);
		assertTrue(ms.undo());
		//If this worked, is should be at the first value of the list
		assertEquals(ms.top(),m);
		//Test if you can undo from cursor = 0
		//You can't do this!
		assertFalse(ms.undo());
		//Double checking if you're cursor remains unchanged
		assertEquals(ms.top(),m);
	}
	
	public void testGetStack()
	{
		Move top1 = new Move(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
		ms.addMove(top1);
		Move top2 = new Move(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
		ms.addMove(top2);
		Move top3 = new Move(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
		ms.addMove(top3);
		Move top4 = new Move(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
		ms.addMove(top4);
		
		assertEquals(ms.getStack().getLast(), top4);
		ms.undo();
		assertEquals(ms.getStack().getLast(), top3);
		ms.undo();
		assertEquals(ms.getStack().getLast(), top2);
		ms.redo();
		assertEquals(ms.getStack().getLast(), top3);
		ms.undo();
		assertEquals(ms.getStack().getLast(), top2);
	}
}
