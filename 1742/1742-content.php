<?php
$url_host = $_SERVER['HTTP_HOST'];

$pattern_document_root = addcslashes(realpath($_SERVER['DOCUMENT_ROOT']), '\\');

$pattern_uri = '/' . $pattern_document_root . '(.*)$/';

preg_match_all($pattern_uri, __DIR__, $matches);

$url_path = $url_host . $matches[1][0];

$url_path = str_replace('\\', '/', $url_path);
?>

<div class="type-1742 p-4">
    <div class="container">
        <div class="row">
            <div class="col-lg-4 col-6 hinh">
                <div class=" position-relative">
                    <img src="images/2.jpg" alt="">

                </div>
                <h3 class=" ">Business Advisory</h3>
                <p>Nulla glavrida amet from amos nulla placerat risus dolor from amet. Donec vestibulum lectus sem, vel convallis ligula commodo ac. Aenean congue placerat risus!</p>
            </div>

            <div class="col-lg-4 col-6 hinh">
                <div class=" position-relative">
                    <img src="images/2.jpg" alt="">

                </div>
                <h3 class="">Business Advisory</h3>
                <p>Nulla glavrida amet from amos nulla placerat risus dolor from amet. Donec vestibulum lectus sem, vel convallis ligula commodo ac. Aenean congue placerat risus!</p>
            </div>

            <div class="col-lg-4 col-6 hinh">
                <div class=" position-relative">
                    <img src="images/2.jpg" alt="">

                </div>
                <h3 class="">Business Advisory</h3>
                <p>Nulla glavrida amet from amos nulla placerat risus dolor from amet. Donec vestibulum lectus sem, vel convallis ligula commodo ac. Aenean congue placerat risus!</p>
            </div>



        </div>
    </div>
</div>