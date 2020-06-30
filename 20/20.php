<?php

$url_host = 'http://' . $_SERVER['HTTP_HOST'];
$pattern_document_root = addcslashes(realpath($_SERVER['DOCUMENT_ROOT']), '\\');
$pattern_uri = '/' . $pattern_document_root . '(.*)$/';

preg_match_all($pattern_uri, __DIR__, $matches);
$url_path = $url_host . $matches[1][0];
$url_path = str_replace('\\', '/', $url_path);

require_once "libs/scss.inc.php";

use ScssPhp\ScssPhp\Compiler;

$scss = new Compiler();
$sass = file_get_contents("sass/style.scss"); //file
$style = $scss->compile($sass);
file_put_contents("css/styles.css", $style);

?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="<?php echo $url_path ?>/css/styles.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>

<body>

    <?php include "./20-content.php"; ?>
    <script type="text/javascript">
        $(document).ready(function() {
            /*== Display the back to top button when scroll ==*/
            $(window).scroll(function() {
                if ($(window).scrollTop() >= 20) {
                    $("#back-to-top").show();
                } else {
                    $("#back-to-top").hide();
                }
            });

            /*== Handle click back to top ==*/
            $("#back-to-top").click(function() {
                $("html, body").animate({
                    scrollTop: 0
                }, 300);
                return false;
            });
        });
    </script>
</body>

</html>