ace.define('ace/mode/eiffel', ['require', 'exports', 'module' , 'ace/lib/oop', 'ace/mode/text', 'ace/tokenizer', 'ace/mode/eiffel_highlight_rules', 'ace/range'], function(require, exports, module) {
"use strict";
var oop = require("../lib/oop");
var TextMode = require("./text").Mode;
var Tokenizer = require("../tokenizer").Tokenizer;
var EiffelHighlightRules = require("./eiffel_highlight_rules").EiffelHighlightRules;
var Range = require("../range").Range;

var Mode = function() {
    this.$tokenizer = new Tokenizer(new EiffelHighlightRules().getRules());
};
oop.inherits(Mode, TextMode);

(function() {
    
    this.toggleCommentLines = function(state, doc, startRow, endRow) {
        var outdent = true;
        var re = /^(\s*)--/;

        for (var i=startRow; i<= endRow; i++) {
            if (!re.test(doc.getLine(i))) {
                outdent = false;
                break;
            }
        }

        if (outdent) {
            var deleteRange = new Range(0, 0, 0, 0);
            for (var i=startRow; i<= endRow; i++)
            {
                var line = doc.getLine(i);
                var m = line.match(re);
                deleteRange.start.row = i;
                deleteRange.end.row = i;
                deleteRange.end.column = m[0].length;
                doc.replace(deleteRange, m[1]);
            }
        }
        else {
            doc.indentRows(startRow, endRow, "--");
        }
    };

    var indentKeywords = {
        "class": 1,
        "create": 1,
        "do": 1,
        "else": 1,
        "feature": 1,
        "if": 1,
        "indexing": 1,
        "inherit": 1,
        "invariant": 1,
        "is": 1,
        "local": 1,
        "note": 1,
        "redefine": 1,
        "rename": 1,
        "require": 1,
        "select": 1,
        "then": 1,
    }

    this.getNextLineIndent = function(state, line, tab) {
        var indent = this.$getIndent(line);

        var tokenizedLine = this.$tokenizer.getLineTokens(line, state);
        var tokens = tokenizedLine.tokens;

        var firstToken = tokens[0];

        do {
            var last = tokens.pop();
        } while (last && (last.type == "comment" || (last.type == "text" && last.value.match(/^\s*$/))));

        if (last) {
            if (indentKeywords[last.value]) {
                indent += tab;
            }
            else if (firstToken.value === "feature") {
                indent += tab;
            }
        }

        return indent;
    };

}).call(Mode.prototype);

exports.Mode = Mode;
});

ace.define('ace/mode/eiffel_highlight_rules', ['require', 'exports', 'module' , 'ace/lib/oop', 'ace/lib/lang', 'ace/mode/text'], function(require, exports, module) {
"use strict";

var oop = require("../lib/oop");
var lang = require("../lib/lang");
var TextHighlightRules = require("./text_highlight_rules").TextHighlightRules;

var EiffelHighlightRules = function() {

    // taken from http://

    var keywords = lang.arrayToMap(
        ("assign|true|false|result"+
        "attached|note|agent|alias|all|and|as|check|"+
        "class|create|creation|debug|deferred|do|else|"+
        "elseif|end|ensure|expanded|export|external|"+
        "feature|from|frozen|if|implies|indexing|infix|"+
        "inherit|inspect|invariant|is|like|local|loop|"+
        "not|obsolete|old|once|or|prefix|redefine|"+
        "rename|require|rescue|retry|select|separate|"+
        "strip|then|undefine|unique|until|variant|when|xor").split("|")
    );

    var builtInTypes = lang.arrayToMap(
        ("INTEGER|STRING|LINKED_LIST|CHARACTER"+
        "BOOLEAN|LIST|ANY|TUPLE|PROCEDURE|FUNCTION|HASH_TABLE").split("|")
    );

    var operators = lang.arrayToMap(
        ("\\+|\\-|\\*|\\/|\\/\\/|\\\\|\\^|\\.\\.|<|>|<=|>=|:="+
        "and|or|xor|and then|or else|implies").split("|")
    );

    // regexp must not have capturing parentheses. Use (?:) instead.
    // regexps are ordered -> the first match is used

    this.$rules = {
        "start" : [
            {
                token : "comment",
                regex : "\\-\\-.*$"
            }, {
                token : "string", // single line
                regex : '["](?:(?:\\\\.)|(?:[^"\\\\]))*?["]'
            }, {
                token : "string", // single line
                regex : "['](?:(?:\\\\.)|(?:[^'\\\\]))*?[']"
            }, {
                token : "constant.numeric", // hex
                regex : "0[xX][0-9a-fA-F]+\\b"
            }, {
                token : "constant.numeric", // float
                regex : "[+-]?\\d+(?:(?:\\.\\d*)?(?:[eE][+-]?\\d+)?)?\\b"
            }, {
                token : "constant.language.boolean",
                regex : "(?:True|False)\\b"
            }, {
                token : function(value) {
                    if (value == "current" || value == "Current")
                        return "variable.language";
                    else if (keywords.hasOwnProperty(value))
                        return "keyword";
                    else if (builtInTypes.hasOwnProperty(value))
                        return "constant.language";
                    else
                        return "identifier";
                },
                regex : "[a-zA-Z_$][a-zA-Z0-9_$]*\\b"
            }, {
                token : "keyword.operator",
                regex : "\\+|\\-|\\*|\\/|\\/\\/|\\\\|\\^|\\.\\.|<=|>=|<|>|:=|\\b(?:and then|or else|and|or|xor|implies)"
            }, {
                token : "lparen",
                regex : "[[({]"
            }, {
                token : "rparen",
                regex : "[\\])}]"
            }, {
                token : "text",
                regex : "\\s+"
            }, {
                token : "support.constant",
                regex : "%."
            }, {
                token : [
                    "string"
                ],
                regex : "[a-zA-Z_$]+\\.[a-zA-Z_$]+\\b"
            }
        ],
    };
    
};

oop.inherits(EiffelHighlightRules, TextHighlightRules);

exports.EiffelHighlightRules = EiffelHighlightRules;
});