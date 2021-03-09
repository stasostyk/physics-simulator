# Physics Simulator
A physics simulator, with various modes to play around with. This project serves as a method for me to apply and revise my knowledge from IB Physics HL.
 * [Kinematics](#kinematics)
 * [Inclined Dynamcics](#incline)
 * [Atwood's Machine](#atwood)
 * [Ideal Gas (Isothermal Process)](#ideal-gas)

# Kinematics
From my physics unit on kinematics in two dimensions, the mode simulates bouncing balls affected by everyday forces, neglecting air resistance.

![Kinematics Demo](images/kinematics_demo.png?raw=true "Kinematics Demo")
* Click your mouse to spawn a ball
* To give it an initial velocity, when you click your mouse, drag it in a direction. The magnitude of your mouse displacement, alongside the angle will give the ball component vectors.
* Note: There are many bugs, such as the "infinite bounce" glitch which you will definitely notice. Well, that's now a feature!

# Incline
I found objects on inclines to be the most interesting application of my unit on dynamics, which is why I made this simulation. It lets you observe the various forces acting to accelerate an object on an inclined surface.

![Dynamics Demo](images/dynamics_demo.png?raw=true "Dynamics Demo")
* Change the sliders on the top to dynamically modify the mass, angle of the incline, and force applied.
* Observe the object either rest, or accelerate up/down. Note: sometimes it will stay still for a second then move. This is intentional, as in reality the object would be moving, but the screen has too little pixels to capture this motion at first.
* To respawn and reset the object, click your mouse and let go where you want it to restart. The red lines will help you navigate.

## Atwood
When learning about more complex mechanics with work, energy, and pulleys, I found the Atwood machine to be the most interesting. I tried connecting these topics together into the atwood pulley.

![Atwood Demo](images/atwood_demo.png?raw=true "Atwood Demo")
* Change the slider values to change the mass of either the left or right mass.
* Pay attention to the change values, such as the potential gravitational energy.

## Ideal-gas
As we finish the thermodynamics unit in class, this simulation is what I found to be most interesting from the new topics. An ideal gas has magnificent properties, some of which I try to model. Marbles on the container push it down, causing a change in volume and pressure (temperature remains constant, making it an isothermal process).

![Ideal Gas Demo](images/idealgas_demo.jpg?raw=true "Ideal Gas Demo")
* Left click over the container (any height above the green bar)
* This dropped a 50kg marble (heavy, I know)!
* Keep spawning more weights and observe the change in state variables as well as visual graphics.
* State variables are represented in the drawing, but for obvious reasons the number of molecules in the drawing is generalized to 30.


## Compiling and Running
To see these simulations on your own, either run the executable .jar file in the build folder or follow the following steps.
1. Download a Java SDK and add it to your system PATH
2. In your terminal or CMD, navigate to the folder in which you have downloaded this project, then into 'build'.
3. Then run:
```
$ java Main
```

Alternatively, double click on 'physics-simulator.jar' in the 'builds' folder to run it!
