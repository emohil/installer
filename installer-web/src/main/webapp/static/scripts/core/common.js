/**
 * Override default toJSON of Date.
 * 
 * @returns {String}
 */
Date.prototype.toJSON = function() {
  var f = function(v) {
    if (v >= 10)
      return v;
    return "0" + v;
  }
  var s = "-", y = this.getFullYear(), m = f(this.getMonth() + 1), d = f(this.getDate());
  return y + s + m + s + d;
}