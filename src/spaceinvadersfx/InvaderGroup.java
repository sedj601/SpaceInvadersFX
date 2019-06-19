/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersfx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

/**
 *
 * @author sedri
 */
public class InvaderGroup extends Group{   
    private final IntegerProperty spaceBetweenInvader = new SimpleIntegerProperty();
    private final IntegerProperty speed = new SimpleIntegerProperty();
    private final IntegerProperty direction = new SimpleIntegerProperty();
    
    private Pane gameBoard;
    
    public InvaderGroup(int numberOfInvaders)
    {
        if(Math.sqrt(numberOfInvaders) % 1 != 0)
        {
            throw new NumberFormatException("The number of invaders must be a perfect square");
        }
        
        for(int i = 0; i < numberOfInvaders; i++)
        {
            this.getChildren().add(new Invader());
        }
        
        this.spaceBetweenInvader.set(5);
        this.speed.set(25);
        this.direction.set(1);
    }
        
    public void setInitLocation(double x, double y)
    {        
        double lastX = x;
        double lastY = y;
        
        int numOfRows = (int)Math.sqrt(this.getChildren().size());
        
        if(!this.getChildren().isEmpty())
        {
            for(int i = 0; i < this.getChildren().size(); i++)
            {
                Invader invader = (Invader)this.getChildren().get(i);
                
                if(i == 0)
                {
                    invader.setLayoutX(lastX);
                    invader.setLayoutY(lastY);
                }
                else
                {
                    if(i % numOfRows == 0)
                    {
                        lastX = x;
                        lastY += spaceBetweenInvader.get() + invader.getHeight();
                    }
                    else
                    {
                        lastX += spaceBetweenInvader.get() + invader.getWidth();
                    }
                    
                    invader.setLayoutX(lastX);
                    invader.setLayoutY(lastY);
                }
            }
        }
    }
    
    public void setGameBoard(Pane gameBoard)
    {
        this.gameBoard = gameBoard;
    }
    
    public boolean moveGroup()
    {
        if(gameBoard == null)
        {
            throw new IllegalStateException("Game board is null! Set the game board!");
        }
        
        if(this.getBoundsInParent().getMinX() <= 0 )
        {
            System.out.println("Hit left wall");
            System.out.println(this.getBoundsInParent().getMinX()+ " - " + gameBoard.getWidth());
            this.direction.set(this.direction.get() * -1);
            final double delta = this.getBoundsInParent().getMinX();            
            this.getChildren().forEach((child) -> {
                Invader invader = (Invader)child;
                invader.setX(invader.getX() + (delta * -1 + 1));
                invader.setY(invader.getY() + 10);
            });  
            
        }
        else if(this.getBoundsInParent().getMaxX() >= gameBoard.getWidth())
        {            
            System.out.println("Hit right wall");
            System.out.println(this.getBoundsInParent().getMaxX() + " - " + gameBoard.getWidth());
            this.direction.set(this.direction.get() * -1);

            final double delta = this.getBoundsInParent().getMaxX() - gameBoard.getWidth();
            this.getChildren().forEach((child) -> {
                Invader invader = (Invader)child;
                invader.setX(invader.getX() - (delta + 1));
                invader.setY(invader.getY() + 10);
            });            
        }
        else 
        {
            System.out.println(this.getBoundsInParent().getMaxX() + " - " + gameBoard.getWidth());
            this.getChildren().forEach((child) -> {
                Invader invader = (Invader)child;
                invader.setX(invader.getX() + (this.direction.get() * this.speed.get()));
            });
        }
        
        return false;
    }
}
