# Dennis Shaykevich
# Assignment 1
# This program sorts 3 to 10 integers in asscending order.

.data
prompt1:	.asciiz "How many integers do you wish to sort? (3 - 10): "		#prompt to ask for Array Size
prompt2: 	.asciiz "Please enter your integers:\n"					#prompt to ask for integers for Array
unsortmsg:	.asciiz "List Entered: "						#unsorted list message
sortedmsg:	.asciiz "\nSorted List: "						#sorted list message
error:		.asciiz "Error: Integer is out of bounds (3, 10). Please try again\n"	#Error message for if Array Size is not valid
space:		.asciiz " "								#Space for seperating Array Integers
value:		.word 0									#Placeholder
theArray:	.space 40								#Dedicated space of Array

.text
main:
#print prompt 1 to string
	li 	$v0, 4         	# Code to print string
	la 	$a0, prompt1 	# Address of string to print
	syscall 		# Print string

check:
#read an integer from user input and checks if in range. If it is, store to size.
	li 	$v0, 5			# Code to input integer
	syscall				# Get the integer
	blt	$v0,  3, errornote	# Goes to errornote if the input is less than 3
	bgt	$v0, 10, errornote	# Goes to errornote main if the input is greater than 10
	li	$v1, 4			#Set v1 to 4
	mult	$v0, $v1		#multiplies size by for to get Array space
	mflo	$t4			#gets lower multiple, used as size
	
	j 	integers		#jump to integers for next step.
	
	
errornote:
#informs user that input is not valid and returns to main.
	li 	$v0, 4         	# Code to print string
	la 	$a0, error 	# Address of string to print
	syscall 		# Print string
	
	j main			#return to main
	
integers:
#print prompt 2 to string
	li 	$v0, 4         	# Code to print string
	la 	$a0, prompt2 	# Address of string to print
	syscall 		# Print to String 
	li	$t1, 0		#set loop interval to 0, dedicated loop register

integersloop:
#Asks user for integers for Array
	li 	$v0, 5			# Code to input integer
	syscall				# Get the integer
	move    $t0, $v0 		# Save input number in t0
	sw	$t0, theArray($t1)	#Save input in array
	addi	$t1, $t1, 4		#increment
	blt	$t1, $t4, integersloop	# If not past the end of theArray, repeat

printlist:
#Prints unsortmsg
	li 	$v0, 4         	# Code to print string
	la 	$a0, unsortmsg 	# Address of string to print
	syscall 		# Print string
	
	li	$t1, 0		#set loop interval to 0
	
printloop:
#Prints all integers in Array (Unsorted)
	lw    	$t5, theArray($t1)	# Gets integer from array
	sw	$t5, value		#save integer in value
	
        lw     	$a0, value  		# Get the lower part of product
	li	$v0, 1          	# Print integer code
	syscall               		# Print the integer
	
        la      $a0, space        	# get space address
	li      $v0, 4           	# print code
        syscall                   	# print a space

	addi	$t1, $t1, 4		#increment
	blt	$t1, $t4, printloop	# If not past the end of theArray, repeat

sort:
#sorting (setting loop interval to 0 outside of loop)
	li	$t1, 0		#set loop interval to 0
	
sortloop:
#does one round of Bubble Sorting.
	addi	$s0, $t1, 0		#set $s0 to $t1
	addi	$s1, $t1, 4		#set $s0 to $t1 + 4 (the next slot in the Array)
	lw	$s3, theArray($s0)	#gets the integer in the Array slot and loads it in $s3
	lw	$s4, theArray($s1)	#gets the integer in the Array slot and loads it in $s4
	bgt	$s3, $s4, flip		#if $s3 is greater than $s4, it goes to flip. (Comparing Adjecent Integers)
	addi	$t1, $t1, 4		#increment
	addi	$t6, $t1, 4		#sets $t6 to $t4 + 4, (for end of array checking).
	blt	$t6, $t4, sortloop	# If not past the end of theArray, repeat
	
	j sorttest
flip:
#If two adjecent integers are in the wrong order, it switches them.
	sw	$s4, value		#Saves $s4 to value
	move	$s4, $s3		#moves $s3 to $4
	lw	$s3, value		#loads value onto $s3
	sw	$s3, theArray($s0)	#inserts $s3 into proper place in area
	sw	$s4, theArray($s1)	#inserts $s4 into proper place in area
	
	j sortloop			#Returns to sort loop

sorttest:
#Checking if sorted (setting loop interval to 0 outside of loop)
	li	$t1, 0		#set loop interval to 0
	
sorttestloop:
#Checks if sorting is nessessery. If it is, it goes back to sort.
	addi	$s0, $t1, 0		#set $s0 to $t1
	addi	$s1, $t1, 4		#set $s0 to $t1 + 4 (the next slot in the Array)
	lw	$s3, theArray($s0)	#gets the integer in the Array slot and loads it in $s3
	lw	$s4, theArray($s1)	#gets the integer in the Array slot and loads it in $s4
	bgt	$s3, $s4, sort		#if $s3 is greater than $s4, it goes back to sort. (Comparing Adjecent Integers)
	addi	$t1, $t1, 4		#increment
	addi	$t6, $t1, 4		#sets $t6 to $t4 + 4, (for end of array checking).
	blt	$t6, $t4, sorttestloop	# If not past the end of theArray, repeat
	
final:
#Prints sortedmsg
	li 	$v0, 4         	# Code to print string
	la 	$a0, sortedmsg 	# Address of string to print
	syscall 		# Print string
	
	li	$t1, 0		#set loop interval to 0
finalloop:
#Prints all integers in Array (Sorted)
	lw    	$t5, theArray($t1)  	# Gets integer from array
	sw	$t5, value		#save integer in value
	
        lw     	$a0, value		# Get the lower part of product
	li	$v0, 1   		# Print integer code
	syscall            	   	# Print the integer
	
        la      $a0, space   	     	# get space address
	li      $v0, 4           	# print code
        syscall                   	# print a space

	addi	$t1, $t1, 4		#increment
	blt	$t1, $t4, finalloop	# If not past the end of theArray, repeat
	
end:
#Ends Program
           li    $v0, 10        # Load exit code
           syscall              # Exit