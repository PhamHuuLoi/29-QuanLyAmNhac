<?php
$url_host = $_SERVER['HTTP_HOST'];

$pattern_document_root = addcslashes(realpath($_SERVER['DOCUMENT_ROOT']), '\\');

$pattern_uri = '/' . $pattern_document_root . '(.*)$/';

preg_match_all($pattern_uri, __DIR__, $matches);

$url_path = $url_host . $matches[1][0];

$url_path = str_replace('\\', '/', $url_path);
?>

<div class="type-20">
    <div class="a"></div>
    <footer id="footer">
        <div class="footer-top">
            <div class="container">
                <div class="row">
                    <div class="col-4">
                        <div class="logo">
                            <img src="https://seeklogo.com/images/W/WorldSkills-logo-5BFEB3BFB4-seeklogo.com.png" alt="" class="img-fluid">
                        </div>
                    </div>
                    <div class="col-8">
                        <div class="social">
                            <div class="float-left mr-5">
                                <span class="white-color font-weight-bold">
                                    Follow us -
                                </span>
                            </div>
                            <div class="float-left">
                                <ul class="social-icon">
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-twitter"></i>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-facebook"></i>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-skype"></i>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-google-plus"></i>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clear-fix"></div>
                </div>
            </div>
        </div>
        <div class="footer-content">
            <div class="container">
                <div class="row">
                    <div class="col-4 about-us">
                        <h1>About us</h1>
                        <div class="address item">
                            <strong>Address:</strong>
                            <span>21 Tran Hung Dao</span>
                        </div>
                        <div class="item">
                            <strong>
                                <i class="fa fa-phone"></i>
                            </strong>
                            <span>0123 456 789</span>
                        </div>
                        <div class="item">
                            <strong>
                                <i class="fa fa-envelope"></i>
                            </strong>
                            <span>huuloi@gmail.com</span>
                        </div>
                        <div class="item">
                            <strong>
                                <i class="fa fa-skype"></i>
                            </strong>
                            <span>abcabc</span>
                        </div>
                    </div>
                    <div class="col-4 explore">
                        <h1>Explore</h1>
                        <div class="row">
                            <div class="col-6">
                                <ul>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-angle-right"></i>
                                            Link
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-angle-right"></i>
                                            Link
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-angle-right"></i>
                                            Link
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-angle-right"></i>
                                            Link
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="col-6">
                                <ul>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-angle-right"></i>
                                            Link
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-angle-right"></i>
                                            Link
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-angle-right"></i>
                                            Link
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-4 recent-news">
                        <h1>Recent news</h1>
                        <div class="row">
                            <div class="col-5">
                                <div class="img">
                                    <a href="#">
                                        <img src="./images/2.jpg" alt="" class="img-fluid">
                                    </a>
                                </div>
                            </div>
                            <div class="col-7 pl-0">
                                <div class="time">
                                    February 21, 2018
                                </div>
                                <div class="title">
                                    <h3>
                                        <a href="#">News title</a>
                                    </h3>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-5">
                                <div class="img">
                                    <a href="#">
                                        <img src="./images/2.jpg" alt="" class="img-fluid">
                                    </a>
                                </div>
                            </div>
                            <div class="col-7 pl-0">
                                <div class="time">
                                    February 21, 2018
                                </div>
                                <div class="title">
                                    <h3>
                                        <a href="#">News title</a>
                                    </h3>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <div class="container">
                <span class="green-color">
                    Like themes
                </span>
                <span class="white-color">
                    2020 &copy; All Right... -
                </span>
                <span class="green-color">
                    Huu Loi Developer
                </span>
            </div>
        </div>
    </footer>

    <a href="#" id="back-to-top">
        <i class="fa fa-angle-up"></i>
    </a>
</div>