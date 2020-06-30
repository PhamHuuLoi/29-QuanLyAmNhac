<?php
$url_host = $_SERVER['HTTP_HOST'];

$pattern_document_root = addcslashes(realpath($_SERVER['DOCUMENT_ROOT']), '\\');

$pattern_uri = '/' . $pattern_document_root . '(.*)$/';

preg_match_all($pattern_uri, __DIR__, $matches);

$url_path = $url_host . $matches[1][0];

$url_path = str_replace('\\', '/', $url_path);
?>

<div class="type-1727 pt-5 pb-5">
    <div class="container s-blog p-4">

        <ul class="nav s-blog-tabs">

            <li>
                <a class="active show" data-toggle="tab" href="#all">View all</a>
            </li>
            <li>

                <a data-toggle="tab" href="#company">Company</a>
            </li>
            <li>
                <a data-toggle="tab" href="#industry">Industry</a>
            </li>
            <li>
                <a data-toggle="tab" href="#media">Media</a>
            </li>
            <li>
                <div class="sort">

                    <i class="far fa-calendar-alt"></i>
                    <a href="#" class="switch-sort">
                        <span></span>
                    </a>
                    <h3>AZ</h3>
                </div>
            </li>
            <li>
                <div class="sort">
                    <i class="fas fa-sort-amount-down"></i>
                    <a href="#" class="switch-sort">
                        <span></span>
                    </a>
                    <i class="fas fa-sort-amount-up"></i>
                </div>
            </li>
        </ul>


    </div>
</div>