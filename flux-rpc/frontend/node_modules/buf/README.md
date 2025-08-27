# buf

A simple utility for converting between Strings and Buffers.

```js
var buf = require('buf');

var aBuffer = buf.hex(strOrBuf);
var aString = buf.to.hex(strOrBuf);
```

## API

- buf(stringOrBuffer, encoding): Buffer - Converts value to a Buffer.
- unbuf(stringOrBuffer, encoding): String - Converts value to a String
  - aliases: `buf.unbuf`, `buf.un`, `buf.str`, `buf.to`
- encoding methods
  - All the encodings that native Buffers can handle are methods on
    `buf` and `unbuf`. These are useful if you'll only ever be using 1
    encoding, such as: `var buf = require('buf').base64; buf(val)`.

## License

MPLv2.0
