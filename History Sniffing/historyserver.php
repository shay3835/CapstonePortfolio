<?php
$code = $_GET['code'];
$string = "Have you ever considered:<br/>";
if ($code == 421356) {
echo "It's time to choose a major.";
} else {
        if ($code[0] == 6){
        $string = $string . "Art History?<br/>";
        }
        if ($code[1] == 3){
        $string = $string . "Chemistry?<br/>";
        }
        if ($code[2] == 5){
        $string = $string . "Economics?<br/>";
        }
        if ($code[3] == 1){
        $string = $string . "English?<br/>";
        }
        if ($code[4] == 4) {
        $string = $string . "Mathematics?<br/>";
        }
        if ($code[5] == 2) {
        $string = $string . "Physics?<br/>";
        }
	if ($string == "Have you ever considered:<br/>"){
	echo "Incorrect Answer. Please Try Again.";
	} else {
	echo $string;
	}
}
?>

