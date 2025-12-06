;(function($) {

    var MenuAim = function(element, options) {
        this.$menu =
        this.activeRow =
        this.mouseLocs =
        this.lastDelayLoc =
        this.timeoutId =
        this.options = null;

        this.init(element, options);
    };

    MenuAim.DEFAULTS = {
        rowSelector: "> li ul",
        submenuSelector: "*",
        submenuDirection: "right",
        tolerance: 75,
        enter: $.noop,
        exit: $.noop,
        activate: $.noop,
        deactivate: $.noop,
        exitMenu: $.noop
    };

    MenuAim.prototype.getDefaults = function() {
        return MenuAim.DEFAULTS;
    };

    MenuAim.prototype.mousemoveDocument = function(e) {
        this.mouseLocs.push({x: e.pageX, y: e.pageY});

        if (this.mouseLocs.length > this.MOUSE_LOCS_TRACKED) {
            this.mouseLocs.shift();
        }
    };

    MenuAim.prototype.mouseleaveMenu = function() {
        if (this.timeoutId) {
            clearTimeout(this.timeoutId);
        }

        if (this.options.exitMenu(this)) {
            if (this.activeRow) {
                this.options.deactivate(this.activeRow);
            }

            this.activeRow = null;
        }
    };

    MenuAim.prototype.mouseenterRow = function(ev) {
        if (this.timeoutId) {
            clearTimeout(this.timeoutId);
        }

        this.options.enter(ev.currentTarget);
        this.possiblyActivate(ev.currentTarget);
    };

    MenuAim.prototype.mouseleaveRow = function(ev) {
        this.options.exit(ev.currentTarget);
    };

    MenuAim.prototype.clickRow = function(ev) {
        this.activate(ev.currentTarget);
    };

    MenuAim.prototype.activate = function(row) {
        if (row == this.activeRow) {
            return;
        }

        if (this.activeRow) {
            this.options.deactivate(this.activeRow);
        }

        this.options.activate(row);
        this.activeRow = row;
    };

    MenuAim.prototype.possiblyActivate = function(row) {
        var delay = this.activationDelay()
          , self = this;

        if (delay) {
            this.timeoutId = setTimeout(function() {
                self.possiblyActivate(row);
            }, delay);
        } else {
            this.activate(row);
        }
    };

    MenuAim.prototype.activationDelay = function() {
        if (!this.activeRow || !$(this.activeRow).is(this.options.submenuSelector)) {
            return 0;
        }

        var offset = this.$menu.offset(),
            upperLeft = {
                x: offset.left,
                y: offset.top - this.options.tolerance
            },
            upperRight = {
                x: offset.left + this.$menu.outerWidth(),
                y: upperLeft.y
            },
            lowerLeft = {
                x: offset.left,
                y: offset.top + this.$menu.outerHeight() + this.options.tolerance
            },
            lowerRight = {
                x: offset.left + this.$menu.outerWidth(),
                y: lowerLeft.y
            },
            loc = this.mouseLocs[this.mouseLocs.length - 1],
            prevLoc = this.mouseLocs[0];

        if (!loc) {
            return 0;
        }

        if (!prevLoc) {
            prevLoc = loc;
        }

        if (prevLoc.x < offset.left || prevLoc.x > lowerRight.x ||
            prevLoc.y < offset.top || prevLoc.y > lowerRight.y) {

            return 0;
        }

        if (this.lastDelayLoc &&
                loc.x == this.lastDelayLoc.x && loc.y == this.lastDelayLoc.y) {

            return 0;
        }

        function slope(a, b) {
            return (b.y - a.y) / (b.x - a.x);
        }

        var decreasingCorner = upperRight,
            increasingCorner = lowerRight;

        if (this.options.submenuDirection == "left") {
            decreasingCorner = lowerLeft;
            increasingCorner = upperLeft;
        } else if (this.options.submenuDirection == "below") {
            decreasingCorner = lowerRight;
            increasingCorner = lowerLeft;
        } else if (this.options.submenuDirection == "above") {
            decreasingCorner = upperLeft;
            increasingCorner = upperRight;
        }

        var decreasingSlope = slope(loc, decreasingCorner),
            increasingSlope = slope(loc, increasingCorner),
            prevDecreasingSlope = slope(prevLoc, decreasingCorner),
            prevIncreasingSlope = slope(prevLoc, increasingCorner);

        if (decreasingSlope < prevDecreasingSlope &&
                increasingSlope > prevIncreasingSlope) {

            this.lastDelayLoc = loc;
            return this.DELAY;
        }

        this.lastDelayLoc = null;
        return 0;
    };

    MenuAim.prototype.destroy = function() {
        this.$menu.removeData("jquery.menu-aim");

        this.$menu.off(".menu-aim")
            .find(this.options.rowSelector)
            .off(".menu-aim");

        $(document).off(".menu-aim");
    };

    MenuAim.prototype.reset = function(triggerDeactivate) {
        if (this.timeoutId) {
            clearTimeout(this.timeoutId);
        }

        if (this.activeRow && triggerDeactivate) {
            this.options.deactivate(this.activeRow);
        }

        this.activeRow = null;
    };

    MenuAim.prototype.init = function(element, option) {
        this.$menu = $(element);
        this.activeRow = null;
        this.mouseLocs = [];
        this.lastDelayLoc = null;
        this.timeoutId = null;
        this.options = $.extend({}, this.getDefaults(), option);

        this.MOUSE_LOCS_TRACKED = 3;  
        this.DELAY = 30000;  

        this.$menu
            .on("mouseleave.menu-aim", $.proxy(this.mouseleaveMenu, this))
            .find(this.options.rowSelector)
                .on("mouseenter.menu-aim", $.proxy(this.mouseenterRow, this))
                .on("mouseleave.menu-aim", $.proxy(this.mouseleaveRow, this))
                .on("click.menu-aim", $.proxy(this.clickRow, this));

        $(document).on("mousemove.menu-aim", $.proxy(this.mousemoveDocument, this));

    };

    $.fn.menuAim = function(opts) {
        return this.each(function() {
            var $this = $(this)
              , data = $this.data("jquery.menu-aim")
              , options = typeof opts == "object" && opts;

            if (!data) {
                $this.data("jquery.menu-aim", (data = new MenuAim(this, options)));
            }

            if (typeof opts == "string") {
                data[opts]();
            }

        });
    };

})(jQuery);