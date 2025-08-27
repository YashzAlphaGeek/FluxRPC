/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

var bufFrom = Buffer.from || Buffer;

function buf(blob, encoding) {
  if (Buffer.isBuffer(blob)) {
    return blob;
  } else {
    return bufFrom(blob, encoding);
  }
}

function unbuf(blob, encoding) {
  if (Buffer.isBuffer(blob)) {
    return blob.toString(encoding);
  } else {
    return blob;
  }
}

function withEncoding(fn, enc) {
  return function encoded(blob) {
    return fn(blob, enc);
  };
}

[
  'ascii',
  'base64',
  'hex',
  'ucs',
  'utf8',
  'utf16le'
].forEach(function(enc) {
  buf[enc] = withEncoding(buf, enc);
  unbuf[enc] = withEncoding(unbuf, enc);
});

module.exports = buf.buf = buf.from = buf;
buf.unbuf = buf.un = buf.str = buf.to = unbuf;
