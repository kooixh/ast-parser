# AST Parser API
Web API to parse an expression into an Abstract Syntax Tree.

Easy, lightweight parsing solution to turn any expression and operators into an AST.

No external parsing tool needed.

## Prerequisite

- Maven 
- Java 8+ 

## Starting the app 

### `make build` to build the app

### `make run` to run it


## Sample API Input 

### POST `/api/parser/parse`

**Request**
```json
{
	"expression": "(x+y)*z",
	"operators": [
		{
			"symbol": "+",
			"precedence": 1,
			"type": "binary"
		},
		{
			"symbol": "-",
			"precedence": 1,
			"type": "binary"
		},
		{
			"symbol": "*",
			"precedence": 2,
			"type": "binary"
		},
		{
			"symbol": "/",
			"precedence": 2,
			"type": "binary"
		}
	]
}
```


**Response**

```json
{
    "status": "success",
    "root": {
        "value": "*",
        "left": {
            "value": "+",
            "left": {
                "value": "x",
                "left": null,
                "right": null
            },
            "right": {
                "value": "y",
                "left": null,
                "right": null
            }
        },
        "right": {
            "value": "z",
            "left": null,
            "right": null
        }
    }
}
```

## References

- [Abstract Syntax Tree](https://en.wikipedia.org/wiki/Abstract_syntax_tree)
- [Shunting Yard Algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm)
- [Chapter 5 of this dissertation](https://kooixiuhong.com/projects/rewrite)
