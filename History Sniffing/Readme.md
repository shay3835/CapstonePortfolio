# PHP History Sniffing Attack
This was my 6th assignment in Professor Yilek's Information Security Class. This demonstrates a data Attack known as History Sniffing. History Sniffing involves a malicious agent acquiring a victim's web visits in ways they otherwise couldn't have and shouldn't have accessed. A popular way of doing so it creating a website with hidden links to particular websites. When a user visits it, by default, unvisited links show up **blue** while visited links were **purple**. In 2010, malicious agents figured out that they could read the color of these links using code and return a list of visited sites. This was patched that same year by making HTML lie about the color of the link. While automatic history sniffing is relatively rare, manual history sniffing (tricking the victim into revealing the link statuses themselves) is somewhat of a common scam. 

This assignment demonstrates an example  of manual history sniffing in the form of fake Captchas. By making a user "verify that they are human", they are actually giving out their data to bad actors. While this fake Captcha is an innocent one that merely recommends majors for students at St. Thomas, real ones can be used for severe targeted advertisement or even figure out which websites you're likely to use the same password in. This assignment helps keep in mind how to make code safe for users and how to protect innocent people from such attacks.

This assignment consists of 2 documents: **historyclient.html**  and **historyserver.php**.

## historyclient.html
This html file consists solely of the fake Captcha. On the surface, it has some text, a series of dice faces that you are meant to deduce, and the textbox for which to input the value. Each die is made up of a 3 x 3 grid of U+2B24 characters, or  "⬤". Black ones for visibility and white ones for proper spaces. Some of them are just plain text, but others are custom colored links.

There are two types of links present: 
**Black Links** which are by default black but become *white* when the link is visited. And...
**White Links** that are white but become *black* when visited.

With the white background, anything white becomes invisible and everything black pops out. These links are carefully places within each box to change the appearance of the die to a different yet valid number. 

The links are associated with different departments of St. Thomas, and are as follows:

https://www.stthomas.edu/catalog/current/arth/
https://www.stthomas.edu/catalog/current/chem/
https://www.stthomas.edu/catalog/current/econ/
https://www.stthomas.edu/catalog/current/engl/
https://www.stthomas.edu/catalog/current/math/
https://www.stthomas.edu/catalog/current/physics/

Visiting any of these links will cause the metadata of the links to change and therefore change color, revealing a new number.

The Captcha as a few tricks to prevent it from being an obvious scam:
1. The links are disabled from being clicked; they only change color. 
2. There is an invisible image in front of it that when looked at is just pure black. This may seem like security but only just prevents the user from hovering over the links.
3. The entry data only allows for 6 characters, exactly the number of die in the Captcha, making it look more authentic. 

When you click the "Check" button, it will then send it to the PHP file.

## historyserver.php
This layer, usually hidden from the user, is where the sniffing occurs. When hooked up to a server, it uses the inputted code to seemingly check the validity of the answer to verify. In actuality, it's collecting data. It will do one of 3 things:

1. **If the code is exactly "421356"**
This indicates that all the recommend sites have been visited, in which case it will print a prompt telling you to pick a major.
2. **If at least one digit matches "635142"**
The the code in step 1 doesn't match, which will then check every digit against this code to determine which sites have not been visited. It will start off by printing a "Have you ever considered:" message, followed by checking each digit of the input to the ones in 635142. For each match, it will print out the name of the department to the corresponding digit.
3. **None of the above**
If the code isn't 421356 or doesn't match a single digit in 635142, this indicates that the user inputted the code wrong. In such a case, it pretends to be an honest Captcha and tell the user that their input was wrong. 
