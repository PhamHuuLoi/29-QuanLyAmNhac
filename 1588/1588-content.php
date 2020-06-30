<?php
$url_host = $_SERVER['HTTP_HOST'];

$pattern_document_root = addcslashes(realpath($_SERVER['DOCUMENT_ROOT']), '\\');

$pattern_uri = '/' . $pattern_document_root . '(.*)$/';

preg_match_all($pattern_uri, __DIR__, $matches);

$url_path = $url_host . $matches[1][0];

$url_path = str_replace('\\', '/', $url_path);
?>

<div class="type-1588">
    <div class="container">
        <h2>OUR CLIENTS</h2>
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide">
                    <div class="col-item">
                        <div class="panel-body">
                            <img src="http://<?php echo $url_path ?>/images/detail/1.png" alt="" />
                        </div>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="col-item">
                        <div class="panel-body">
                            <img src="http://<?php echo $url_path ?>/images/detail/1.png" alt="" />
                        </div>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="col-item">
                        <div class="panel-body">
                            <img src="http://<?php echo $url_path ?>/images/detail/1.png" alt="" />
                        </div>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="col-item">
                        <div class="panel-body">
                            <img src="http://<?php echo $url_path ?>/images/detail/1.png" alt="" />
                        </div>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="col-item">
                        <div class="panel-body">
                            <img src="http://<?php echo $url_path ?>/images/detail/1.png" alt="" />
                        </div>

                    </div>
                </div>

            </div>

            <!-- Add Arrows -->
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>

        </div>

    </div>

</div>