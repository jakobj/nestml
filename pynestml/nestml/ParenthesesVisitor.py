#
# ParenthesesVisitor.py
#
# This file is part of NEST.
#
# Copyright (C) 2004 The NEST Initiative
#
# NEST is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 2 of the License, or
# (at your option) any later version.
#
# NEST is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with NEST.  If not, see <http://www.gnu.org/licenses/>.

"""
expression : leftParentheses='(' term=expression rightParentheses=')'
"""
from pynestml.nestml.NESTMLVisitor import NESTMLVisitor


class ParenthesesVisitor(NESTMLVisitor):
    """
    Visits a single expression encapsulated in brackets and updates its type.
    """

    def visitExpression(self, _expr=None):
        exprTypeE = _expr.getExpression().getTypeEither()
        _expr.setTypeEither(exprTypeE)
        return