"use strict";

function _typeof(obj) { "@babel/helpers - typeof"; if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }

;

var uiCommon = function (uiCommon, $window) {
  uiCommon.init = function () {
    uiCommon.eventBind.init(); // 이벤트 바인딩
    // component

    $('[data-pop]').length > 0 && uiCommon.layer.init(); // layer popup
  }; // Body Scroll Fix


  uiCommon.bodyFix = {
    on: function on() {
      var $wrap = $('#wrap');
      var wScroll = $window.scrollTop();
      $('body').addClass('scrOff').css({
        'top': -wScroll
      });
    },
    off: function off() {
      var wScroll = Math.abs(parseInt($('body').css('top')));
      $('body').removeClass('scrOff').removeAttr('style');
      $window.scrollTop(wScroll);
    }
  }; // slick 생성 객체

  uiCommon.slick = {
    init: function init(el, opt) {
      return el.not('.slick-initialized').slick(opt);
    },
    destory: function destory(el) {
      el.filter('.slick-initialized').slick('unslick');
    }
  }; // lottie 생성 객체

  uiCommon.lottie = {
    init: function init($el, file) {
      lottie.loadAnimation({
        container: document.getElementById($el),
        renderer: 'svg',
        loop: true,
        autoplay: true,
        path: '../../../assets/web/images/lottie/' + file + '.json'
      });
    }
  }; // Layer Popup

  uiCommon.layer = {
    init: function init() {
      var _this = this;

      $('[data-pop]').on('click', function (e) {
        return _this.open(e);
      });
      $('.popup-close').on('click', function (e) {
        return _this.close(e);
      });
    },
    open: function open($target) {
      var _id;

      if (_typeof($target) == 'object') {
        _id = '#' + $($target.currentTarget).attr('data-pop');
      } else {
        _id = '#' + $target;
      }

      this.obj = $($target.currentTarget);
      uiCommon.bodyFix.on();

      if (!$(_id).find('.layer-popup').hasClass('alert-popup') && !$(_id).find('.layer-popup').hasClass('slide-popup') && !$(_id).find('.layer-popup').hasClass('img-popup')) {
        this.scroll($(_id));
      }

      if ($(_id).find('.layer-popup').hasClass('slide-popup')) {
        this.slider($(_id));
      }

      $(_id).attr({
        tabindex: 0,
        "aria-hidden": "false"
      }).stop().fadeIn().css('display', 'flex').addClass('show').focus();
    },
    close: function close($target) {
      if (_typeof($target) == 'object') {
        $($target.currentTarget).closest('.layer-popup-wrap').attr({
          tabindex: "",
          "aria-hidden": "true"
        }).stop().fadeOut().removeClass('show');
      } else {
        $('#' + $target).attr({
          tabindex: "",
          "aria-hidden": "true"
        }).stop().fadeOut().removeClass('show');
      }

      uiCommon.bodyFix.off();
      this.obj.focus();
    },
    scroll: function scroll($target) {
      var $popWrap = $target.find('.layer-popup'),
          $popBody = $target.find('.popup-body');

      $.fn.hasScrollBar = function () {
        return this.get(0).scrollHeight > this.innerHeight();
      };

      setTimeout(function () {
        if ($popBody.hasScrollBar()) {
          $popWrap.addClass('isScroll');
          $popBody.on('wheel scroll', function () {
            $(this).scrollTop() > 0 ? $popWrap.addClass('down') : $popWrap.removeClass('down');
          });
        }
      }, 100);
    },
    slider: function slider($target) {
      var $el = $target.find('.popup-slider'),
          _length = $el.find('> div').length; // 팝업 슬라이더

      setTimeout(function () {
        if ($el) {
          $el.on({
            init: function init(event, slick) {
              $('.slider-count .total').text(_length);
            },
            beforeChange: function beforeChange(event, _ref, currentSlide, nextSlide) {
              var count = _ref.slideCount;
              $('.slider-count .current').text(nextSlide + 1);
              console.log($(this));
            }
          });
          uiCommon.slick.init($el, {
            infinite: true,
            speed: 500,
            slidesToShow: 1,
            dots: false,
            arrows: false
          });
        }
      }, 100);
    }
  };
  uiCommon.eventBind = {
    init: function init() {
      // 공통 이벤트
      $('header').length > 0 && this.header.init(); // 헤더 관련 이벤트

      $('.tab-wrap').length > 0 && this.tab.init(); // 탭 관련 이벤트

      $('.box-tooltip').length > 0 && this.tooltip.init(); // 툴팁 이벤트

      $('.btn-toast').length > 0 && this.toast.init(); // 토스트

      $('.box-input-write .box-input textarea').length > 0 && this.commentInput.init(); // 댓글입력 관련 이벤트

      $('.accordion-head button').length > 0 && this.accordion.init(); // 아코디언 이벤트

      $('.rolling-list').length > 0 && this.rolling.init(); // 롤링 이벤트

      $('.text-input-wrap').length > 0 && this.textField.init(); // 텍스트 필드 이벤트
      // 각 플러그인 객체 생성 호출

      this.slider.init(); // 슬라이더 생성호출 이벤트

      this.lottie.init(); // 로티 생성호출 이벤트

      this.floatingBtn.init(); // 플로팅 버튼
      // $('.plugin-select').length > 0 && this.selectric.init(); // 셀렉트 플러그인 생성호출 이벤트
      // 메뉴별 이벤트

      $('.story-detail').length > 0 && this.storyDetail.init(); // 스토리 상세 페이지 관련 이벤트

      $('.layout-write-wrap').length > 0 && this.writeEvent.init(); // 작성 페이지 관련 이벤트
    },
    header: {
      init: function init() {
        this.gnb(); // gnb

        this.search(); // 헤더 검색 이벤트
        // $window.on('scroll', () => {
        //     $(window).scrollTop() > 0 ? $('header').addClass('scrolling') : $('header').removeClass('scrolling');
        // });
      },
      search: function search() {
        var $el = $('.box-hd-sch'),
            $input = $el.find('.sch-input'),
            $del = $el.find('.ico-search-delete'),
            $schBox01 = $el.find('.box-sch-type1'),
            $schBox02 = $el.find('.box-sch-type2');

        function validate() {
          var currentVal = $input.val();

          if (currentVal !== '') {
            $del.show();
            $schBox01.removeClass('on');
            $schBox02.show();
          } else {
            $del.hide();
            $schBox01.addClass('on');
            $schBox02.hide();
          }
        } // 검색 input 타이핑 이벤트


        setTimeout(function () {
          $input.on('change keyup', function () {
            return validate();
          });
        }, 100); // input 내용 초기화

        $del.on('click', function () {
          $(this).hide();
          $schBox02.hide();
          $input.val('').focus();
        }); // input 포커싱

        $input.on('focus', function () {
          validate();
          $input.addClass('isFocus');
        });
        document.querySelector('body').addEventListener('click', function (_ref2) {
          var target = _ref2.target;

          if (target.closest('.box-hd-sch')) {
            validate();
            $input.addClass('isFocus');
          } else {
            $input.removeClass('isFocus');
            $schBox01.removeClass('on');
            $schBox02.hide();
            $del.hide();
          }
        });
      },
      gnb: function gnb() {
        var $hamburger = $('.btn-hamburger'),
            $menu = $('.gnb-wrap'),
            $dep1 = $('.dep1.isDep2'); // gnb menu open

        $hamburger.on('click', function () {
          $menu.fadeIn().addClass('open'); // uiCommon.bodyFix.on();
        }); // gnb menu close

        $menu.on('click', function (event) {
          var $className = event.target.className;

          if ($className === 'gnb-wrap open') {
            $menu.hide().removeClass('open'); // uiCommon.bodyFix.off();
          }
        }); // dep2 slide

        $dep1.on('click', function () {
          $(this).toggleClass('open');
          $(this).siblings('.dep2-list').slideToggle('fast');
        });
      }
    },
    tab: {
      init: function init() {
        $('.tab-wrap').hasClass('tab-ctrl') && this.tabEvent(); // 탭 조작 이벤트

        $('.tab-wrap .ico-1depth-close').length > 0 && this.tabDropDown(); // dropdown 이벤트
      },
      tabEvent: function tabEvent() {
        $(".tab-list > li").eq(0).find("a, button").attr({
          title: "현재 탭",
          "aria-selected": "true",
          role: "tab"
        }).addClass('on');
        $(".tab-content").find("> div").eq(0).attr("aria-hidden", "false").siblings("div").attr("aria-hidden", "true");
        $(".tab-ctrl .tab-list > li > *").on("focusin click", function () {
          var i = $(this).closest("li").index();
          $(this).addClass("on").attr({
            title: "현재 탭",
            "aria-selected": "true"
          }).closest("li").siblings("li").find("a").removeClass("on").attr({
            title: "",
            "aria-selected": "false"
          });
          $(this).closest(".tab-list").siblings(".tab-content").children("div").attr("aria-hidden", "false").eq(i).show().siblings("div").hide().attr("aria-hidden", "true");
        });
      },
      tabDropDown: function tabDropDown() {
        var $list = $('.tab-list-round'),
            $btn = $('.ico-1depth-close'); // click event

        $btn.on('click', function () {
          $(this).closest('.btn-tab-wrap').siblings('.tab-list').toggleClass('active');
        }); // wheel event

        $list.on('wheel', function (event) {
          event.preventDefault();
          var scrollY = $list.scrollLeft();
          $(this).scrollLeft(scrollY += event.originalEvent.deltaY);
        }); // tab more btn show? hide?

        $list.each(function (index, item) {
          var tabW = $(item).width(),
              arrTab = $(item).find('> li');

          var sumW = 0,
              _index;

          arrTab.each(function (index, listItem) {
            var itemW = $(listItem).width();

            if ($(listItem).find('.on').parent().index() != -1) {
              _index = $(listItem).find('.on').parent().index() - 1;
            }

            sumW += itemW;
          });

          if (tabW <= sumW) {
            $(item).siblings('.btn-tab-wrap').show();
            $(item).addClass('isScroll');
            if (_index > 6) $(item).scrollLeft(_index * 60);
          }
        });
      }
    },
    floatingBtn: {
      init: function init() {
        var $write = $(".btn-write-bottom");

        if ($('#container').hasClass('isWriteBtn')) {
          $write.show().css('display', 'flex');
          setTimeout(function () {
            $write.addClass('on');
          }, 1000);
        }
      }
    },
    tooltip: {
      init: function init() {
        this.open();
        this.close();
      },
      open: function open() {
        var $btn = $('.btn-tooltip');
        $btn.on('click', function () {
          $(this).siblings('.active-toolBox').toggleClass('active');
        });
      },
      close: function close() {
        document.querySelector('body').addEventListener('click', function (_ref3) {
          var target = _ref3.target;
          !target.closest('.box-tooltip') && $('.active-toolBox').removeClass('active');
        });
      }
    },
    toast: {
      init: function init() {
        var $btn = $('.btn-toast');
        $btn.on('click', function () {
          if (!$(this).siblings('.pop-toast').hasClass('show')) {
            $(this).siblings('.pop-toast').addClass('show');
            setTimeout(function () {
              $('.pop-toast').removeClass('show');
            }, 2000);
          }
        });
      }
    },
    slider: {
      init: function init() {
        // 커뮤니티 메인
        if ($('.popular-list').length) {
          var $el = uiCommon.slick.init($('.popular-list'), {
            infinite: false,
            speed: 500,
            slidesToShow: 4,
            slidesToScroll: 4,
            dots: false
          });
          $('.slick-next').on('click', function () {
            if ($(this).hasClass('slick-disabled')) {
              $('.popular-list').slick('slickGoTo', 0);
            }
          });
        } // 챌린지 서브메인


        if ($('.ev-slide').length) {
          var _$el = uiCommon.slick.init($('.ev-slide'), {
            infinite: false,
            speed: 500,
            slidesToShow: 4,
            slidesToScroll: 4,
            dots: false
          });

          $('.slick-next').on('click', function () {
            if ($(this).hasClass('slick-disabled')) {
              $('.ev-slide').slick('slickGoTo', 0);
            }
          });
        } // 공지사항 슬라이드


        if ($('.slider-wrap').length && $('.box-notice-list').length) {
          var _$el2 = uiCommon.slick.init($('.box-notice-list'), {
            infinite: false,
            speed: 500,
            slidesToShow: 3,
            slidesToScroll: 3,
            dots: false
          });

          $('.slick-next').on('click', function () {
            if ($(this).hasClass('slick-disabled')) {
              $('.box-notice-list').slick('slickGoTo', 0);
            }
          });
        }
      }
    },
    lottie: {
      init: function init() {
        // 검색결과가 없습니다./관심작가가 없습니다.
        $('#not-search').length > 0 && uiCommon.lottie.init('not-search', '01_coffee_cup'); //이벤트-챌린지

        $('#not-chellenge').length > 0 && uiCommon.lottie.init('not-chellenge', '06_challenge'); //페이지를 찾을 수 없습니다.

        $('#not-page').length > 0 && uiCommon.lottie.init('not-page', '03_error_page'); //웰카페

        $('#not-pin').length > 0 && uiCommon.lottie.init('not-pin', '02_wellcafe_pin'); // 등록된 게시물이 없습니다.

        $('#not-board').length > 0 && uiCommon.lottie.init('not-board', '08_community_empty'); // 완료 체크 로티

        $('#icon-lottie06').length > 0 && uiCommon.lottie.init('icon-lottie06', '07_complete');
      }
    },
    accordion: {
      init: function init() {
        var $btn = $('.accordion-head button'),
            $body = $('.accordion-body');
        $btn.on('click', function () {
          if (!$(this).hasClass('open')) {
            $btn.removeClass('open');
            $body.stop().slideUp('fast');
            $(this).addClass('open');
            $(this).closest('.accordion-wrap').find('.accordion-body').stop().slideDown('fast');
          } else {
            $btn.removeClass('open');
            $body.stop().slideUp('fast');
          }
        });
      }
    },
    commentInput: {
      init: function init() {
        var $el = $('.box-input-write .box-input textarea'),
            hiddenDiv = $(document.createElement('div'));
        var content = null;
        hiddenDiv.addClass('tarea-hidden');
        $el.after(hiddenDiv);
        $el.on('keyup', function () {
          content = $(this).val();
          content = content.replace(/\n/g, '<br>');
          $(this).siblings('.tarea-hidden').html(content + '<br class="lbr">');
          $(this).css('height', $(this).siblings('.tarea-hidden').height());
        });
      }
    },
    storyDetail: {
      init: function init() {
        this.headerNavi();
        this.bottomNavi();
      },
      headerNavi: function headerNavi() {
        var $header = $('header'),
            $storyHeader = $('.story-detail-header'),
            $progressBar = $('.progressbar .in-progress');
        var b,
            c = 0,
            d = 100,
            pg_width;
        $window.on('scroll', function () {
          var scTop = $(this).scrollTop();
          b = $(document).height() - $(window).height();
          pg_width = scTop * (d - c) / b + c;
          $progressBar.css('width', pg_width + '%');

          if (scTop > $header.height() || $('body').css('top') != '0px') {
            $header.hide();
            $storyHeader.stop().fadeIn('fast');
          } else {
            $header.show();
            $storyHeader.stop().fadeOut('fast');
          }
        });
      },
      bottomNavi: function bottomNavi() {
        var $el = $('.layout-detail-view'),
            winH = $(window).height(),
            $utilPo = $el.find('.detail-util').offset().top - winH - 300;
        $el.addClass('sticky');
        $window.on('scroll', function () {
          var scTop = $(this).scrollTop();
          scTop >= $utilPo ? $el.removeClass('sticky') : $el.addClass('sticky');
        });
      }
    },
    rolling: {
      init: function init() {
        var $list = $('.rolling-list'),
            $count = $('.rolling-count'),
            $arrow = $('.rolling-arrow');
        $count.find('.total').text($list.find('> div').length);
        $list.on({
          beforeChange: function beforeChange(event, _ref4, currentSlide, nextSlide) {
            var count = _ref4.slideCount;
            $count.find('.current').text(nextSlide + 1);
          }
        });
        uiCommon.slick.init($list, {
          infinite: true,
          speed: 500,
          slidesToShow: 1,
          dots: false,
          arrows: false,
          autoplay: true,
          autoplaySpeed: 3000,
          vertical: true
        });
        $arrow.find('.prev').on('click', function () {
          return $list.slick('slickPrev');
        });
        $arrow.find('.next').on('click', function () {
          return $list.slick('slickNext');
        });
      }
    },
    selectric: {
      init: function init() {
        $('.plugin-select').selectric({
          maxHeight: 284,
          onChange: function onChange(element) {
            $(element).closest('.selectric-wrapper').find('.selectric').addClass('change');
          }
        });
      }
    },
    writeEvent: {
      init: function init() {
        this.guideBox();
        this.moduleList();
      },
      guideBox: function guideBox() {
        var $el = $('.box-guide');
        setTimeout(function () {
          return $el.addClass('hide');
        }, 3000);
      },
      moduleList: function moduleList() {
        var $wrap = $('.box-module-wrap'),
            $list = $('.list-module'),
            _top = $wrap.offset().top - 100;

        setTimeout(function () {
          $list.find('> li').hover(function () {
            $(this).siblings('li').find('button').addClass('unfocus');
          }, function () {
            $(this).siblings('li').find('button').removeClass('unfocus');
          });
        }, 500);
        $window.on('scroll', function () {
          var scTop = $(this).scrollTop();
          scTop >= _top ? $wrap.addClass('fix') : $wrap.removeClass('fix'); // if ( scTop >= _top ) {
          //     $wrap.css('top', '70px');
          // }
        });
        $('.ico-post-mov').on('click', function () {
          $(this).siblings('.layer-module-select').toggleClass('on');
        });
        document.querySelector('body').addEventListener('click', function (_ref5) {
          var target = _ref5.target;

          if (!target.closest('.ico-post-mov')) {
            $('.layer-module-select').removeClass('on');
          }
        });
      }
    },
    textField: {
      init: function init() {
        this["delete"]();
        $('.text-input-wrap.isBtn').length > 0 && this.actBtn();
      },
      actBtn: function actBtn() {
        var $el = $('.text-input-wrap'),
            $input = $el.find('input'); // input 타이핑 이벤트

        setTimeout(function () {
          $input.on('change keyup', function () {
            var currentVal = $(this).val(),
                btn = $(this).siblings('.btn-action');

            if (currentVal !== '') {
              btn.removeAttr('disabled');
              btn.addClass('btn-black');
            } else {
              btn.attr('disabled', '');
              btn.removeClass('btn-black');
            }
          });
        }, 100);
      },
      "delete": function _delete() {
        var $el = $('.text-input-wrap'),
            $input = $el.find('input'),
            $del = $el.find('.ico-search-delete'); // input 타이핑 이벤트

        setTimeout(function () {
          $input.on('change keyup', function () {
            var currentVal = $(this).val(),
                btn = $(this).siblings('.ico-search-delete');
            currentVal !== '' ? btn.show() : btn.hide();
          });
        }, 100);
        $del.on('click', function () {
          $(this).hide();
          $(this).siblings('input').val('').focus();
        });
        document.querySelector('body').addEventListener('click', function (_ref6) {
          var target = _ref6.target;

          if (!target.closest('.text-input-wrap')) {
            $del.hide();
          }
        });
      }
    }
  };
  uiCommon.init();
  return uiCommon;
}(window.uiCommon || {}, $(window));