<?php
$url_host = $_SERVER['HTTP_HOST'];

$pattern_document_root = addcslashes(realpath($_SERVER['DOCUMENT_ROOT']), '\\');

$pattern_uri = '/' . $pattern_document_root . '(.*)$/';

preg_match_all($pattern_uri, __DIR__, $matches);

$url_path = $url_host . $matches[1][0];

$url_path = str_replace('\\', '/', $url_path);
?>

<div class="type-1774">
    <div class="container">

        <div class="row">

            <div class="col-md-6"></div>
            <div class="col-md-6">
                <h3>OUT COMPANY IN PHOTOS</h3>
                <div class="row">

                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="item">
                            <div class="icon">
                                <img src="images/1.jpg" alt="" />
                            </div>
                            <div class="text">
                                <a href="">
                                    <i class="fa fa-briefcase info-circle-icon"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>