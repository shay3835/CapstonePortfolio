# MIPS Bubblesort
This was the first assignment I've done in Professor Lamb's Computer Architecture course. This demonstrates the process of deeper level computing through a simple process, demonstrating the intricate and more esoteric processes needed for a computer to preform even the most simplest tasks. Many of the tasks that are needed to be done are more manual than the same task in more surface level languages. The assignment consists of one 1 file, **Assignment1.asm**, a MIPS file that takes 3 to 10 integers as input and returns them in ascending order. 

It will check the whether a proper number integers to sort is inputted, take those integers to be sorted as input, prints them out unsorted, sorts them, and then prints them out sorted.

## .data
The program runs in the order it is written unless instructed otherwise. Here in the **.data** section is where all the variables that will be used are initialized. These are mostly lines that will be printed for various reasons, including prompts, messages, and errors. These are defined with the **.asciiz** variable type and each have unique names. We also have a **.word** variable of value that is initialized as 0, which will be the variable used to temporarily store values. Finally, we have a defined array to where our values to be stored will be need. It has the **.space** variable attached to it, indicating to the number bytes ahead to allocate so that the array can be defined without conflict. Since each integer in MIPS used 4 bytes and we have a maximum of 10 integers to sort, we allocate 40 bytes. 

## .text
This **.text** section where all the methods are and where the rest of the computing will take places. Each method must have a unique name that doesn't conflate with those in .data. The methods are as follows:

## main:
This begins the application. It first uses the line `li $v0, 4`, which is a command to prepare something for print. Then it reads the line `la $a0, prompt1`, to get the address for the first prompt asking the number of variables the user wants sorted.   Finally, `syscall` is read executing the print. These three often come together throughout the code, so I'll just refer to this process as printing to avoid repetition. Many other lines group up together as well, so I'll do the same to them.

## check:
This method checks to see that the inputted value is in fact between 3 and 10. It does with with `li $v0, 5` which listens for a integer input storing it in spot $v0. After `syscall`, it does 2 compares: `blt	$v0,  3, errornote`
`bgt	$v0,  10, errornote`
These are the byte less than and byte greater than variables, comparing to see if our entered value is less than 3 or greater than 10. If it is, it runs the errornote method which will be seen later on. Otherwise, it runs `li $v1 4`. Li outside of $v0 usually sets the value to whatever inputted, in this case 4. Two more lines are run: 
`mult $v0, %v1`
`mflo $t4`
These effectively multiplies our two values together (input and 4) together and stores it in $t4 for later use to help determine the size of the space we are working with. That done, it runs `j integers`, a jump function to the integers method.
## errornote:
If one of the tests in the previous method comes back true, it prints an error message stating that input was invalid and jumps back to the main to start over.

## integers:
This first prints the second prompt to start inputting values. `li	$t1, 0` is then used to start a loop interval of 0 in the dedicate $t1.

## integersloop:
This ask for an integer input, the uses 
`move    $t0, $v0` to move the value to $t0 for storage. It saves it to an array slot with `sw	$t0, theArray($t1)` with out previous dedicate loop interval slot. It increments the $t1 by 4 with `addi	$t1, $t1, 4` and does a comparison to see if the loop is yet to reach the end with `blt	$t1, $t4, integersloop`. If it's still not their, it redoes the method, else it moves on. 
## printlist:
This method prints a message indicating it will print the entered unsorted array and reinitializes $t1 for another loop. 
## printloop:
For each value in the array, it loads the value in the spot $t1 is point to with `lw    	$t5, theArray($t1)`. It then saves to our previously allocated value variable then then pulled back again to be printed. This is done with
`sw	$t5, value`
`lw $a0, value`
The $a0 is an important slot of integer printing. That is done with 
`li	$v0, 1`
`syscall`
The $t1 is then incremented and there is a check if the loop is through.
## sort:
Sort begins the sorting process by initializing the loop again.
## sortloop:
This method does 1 round of Bubble sorting. It initializes two slots, one for the pointer and one for the value after the pointer.
`addi	$s0, $t1, 0`
`addi	$s1, $t1, 4`
It then pulls the two pointed values out of the array with
`lw	$s3, theArray($s0)`
`lw	$s4, theArray($s1)`
and then checking to see if they are out of order with `bgt	$s3, $s4, flip`.
If they are, it jumps to flip, otherwise, it increments $t1 and does `addi	$t6, $t1, 4	` to have value to see if they are at the end of the array. This is done with the compare `blt	$t6, $t4, sortloop`. If they aren't at the end, it loops back to the beginning to compare the next pair, otherwise it moves on and jumps to sorttest.
## flip:
This method juggles what is stored in $s3 and $s4 by temporarily storing it in value to flip them around, then saves the values in the right order with 
`sw	$s3, theArray($s0)`
`sw	$s4, theArray($s1)`.
It then jumps jack to sortloop: to continue the bubble sort process.
## sorttest:
This method prepares to check if the array is sorted by reinitializing $t1 back to 0 to do more looping. 
## sorttestloop:
sorttestloop: is almost identical to sortloop:. However, when they find something out of order, instead of going to flip:, it jumps to sort: to begin another round of sorting. If it reaches the end of the looping and finds all values are in order, it moves on.
## final:
This prints out the sorted array prompt and reinitializes $t1 for the last time.

## finalloop:
finalloop: is a copy of printloop: printing out all the integers in order. 

## end: 
This is the final method of any MIPS document in order to prevent the reader from going to far into the variables. It's loads the exit code and exits with the following lines. 
`li    $v0, 10`
`syscall`

##
This complicated and unintuitive process just to sort some integers shows the complexity of deep computer computing compared to what most programmers deal with nowadays on the surface.
